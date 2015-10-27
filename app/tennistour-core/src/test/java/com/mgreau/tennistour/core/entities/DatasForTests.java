package com.mgreau.tennistour.core.entities;

import java.util.*;

/**
 * Create datas samples to test entities.
 * 
 * @author mgreau
 * 
 */
public class DatasForTests {

	public static final String[] TOURNAMENTS = { "FRENCH OPEN", "US OPEN",
			"AUSTRALIAN OPEN" };

	public static final String[] PLAYERS = { "DJOKOVIC", "NADAL", "FEDERER",
			"TSONGA" };

	public static enum ASSOCIATION {
		WTA, ATP;
	}

	public static enum SURFACE {
		CLAY, HARD, CAPET, GRASS;
	}

	public static enum COURT {
		OUTDOOR, INDOOR;
	}

	public static List<Tournament> getTournaments() {
		List<Tournament> l = new ArrayList<>();
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		
		

		Tournament t = new Tournament();
		t.setName(TOURNAMENTS[0]);
		t.setBestOfSet(5);
		t.setCourt(COURT.OUTDOOR.name());
		t.setSurface(SURFACE.CLAY.name());
		t.setIdAssociation(32);
		t.setLocation("PARIS");
		t.setYear(c.get(Calendar.YEAR)-10);
		t.setTypeAssociation(ASSOCIATION.ATP.name());
		//t.setId(1);

		Tournament t2 = new Tournament();
		t2.setName(TOURNAMENTS[1]);
		t2.setBestOfSet(5);
		t2.setCourt(COURT.INDOOR.name());
		t2.setSurface(SURFACE.HARD.name());
		t2.setIdAssociation(50);
		t2.setLocation("NEW YORK");
		t2.setTypeAssociation(ASSOCIATION.ATP.name());
		t2.setYear(2013);
		//t2.setId(2);

		Tournament t3 = new Tournament();
		t3.setName(TOURNAMENTS[2]);
		t3.setBestOfSet(5);
		t3.setCourt(COURT.OUTDOOR.name());
		t3.setSurface(SURFACE.HARD.name());
		t3.setIdAssociation(32);
		t3.setTypeAssociation(ASSOCIATION.ATP.name());
		t3.setLocation("MELBOURNE");
		t3.setYear(2011);
		//t3.setId(3);

		l.add(t);
		l.add(t2);
		l.add(t3);
		return l;
	}

	public static List<Player> getPlayers() {
		List<Player> l = new ArrayList<>();
		Random r = new Random();
		for (String name : PLAYERS) {
			Player p = new Player();
			p.setName(name);
			p.setSexe('M');
			//p.setId(r.nextInt());
			l.add(p);
		}
		return l;
	}
	
	public static List<Match> getMatches() {
		List<Match> l = new ArrayList<>();
		Match m = new Match();
		m.setMatchDate(new Date());
		//m.setId(1);
		m.setRound("Final");
		m.setState("FINISHED");
		l.add(m);
		return l;
	}

}
