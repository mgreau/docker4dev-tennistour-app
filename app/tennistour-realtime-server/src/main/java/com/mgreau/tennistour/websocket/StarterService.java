package com.mgreau.tennistour.websocket;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.mgreau.tennistour.core.TennisMatch;
import com.mgreau.tennistour.websocket.messages.MatchMessage;

import redis.clients.jedis.BinaryJedis;

@Startup
@Singleton
public class StarterService {

    //private Random random;
    //private Map<String, TennisMatch> matches = new ConcurrentHashMap<>();

    private static final Logger logger = Logger.getLogger("StarterService");

    private BinaryJedis jedis;

    private final String jedisKey = "matches";

    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "Initializing WebSocket App.");
        jedis = new BinaryJedis("redis-cache");
    }
    
    @Schedule(second="*/2", minute="*",hour="*", persistent=false)
    public void play() {

    	Set<byte[]> matches = jedis.smembers(jedisKey.getBytes());

        try {
            for (Iterator<byte[]> it = matches.iterator(); it.hasNext();) {
                TennisMatch m = (TennisMatch) deserialize(it.next());

                logger.info("Get from cache Key: " + m.getKey());
                MatchEndpoint.send(new MatchMessage(m), m.getKey());
                //if there is a winner, send result and reset the game
                if (m.isFinished()) {
                    MatchEndpoint.sendBetMessages(m.playerWithHighestSets(), m.getKey(), true);
                }
            }
        }catch (Exception ex){
            logger.severe("Error with Cache " + ex.getCause());
            ex.printStackTrace();
        }
        Long nbMatches = jedis.scard(jedisKey.getBytes());
        logger.info("Number of matches: " + nbMatches);

    }

    public BinaryJedis getCache(){
        return jedis;
    }

    public TennisMatch getMatchFromCache(final Long index) throws Exception{
        //TODO: find how to get one element
        for (Iterator<byte[]> it = jedis.smembers(jedisKey.getBytes()).iterator(); it.hasNext();) {
            TennisMatch m = (TennisMatch) deserialize(it.next());
            if (new Long(m.getKey()) == index){
                return m;
            }
        }
        return null;
    }

    public Collection<TennisMatch> getAllMatchesFromCache() throws Exception{
        Set<TennisMatch> setMatches = new HashSet<>();

        Set<byte[]> matches = jedis.smembers(jedisKey.getBytes());

        try {
            for (Iterator<byte[]> it = matches.iterator(); it.hasNext();) {
                TennisMatch m = (TennisMatch) deserialize(it.next());
                setMatches.add(m);
            }
        }catch (Exception ex){
            logger.severe("Error with Cache " + ex.getCause());
            ex.printStackTrace();
        }

        return setMatches;
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}
