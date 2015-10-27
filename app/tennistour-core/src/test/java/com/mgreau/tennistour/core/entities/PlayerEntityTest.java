package com.mgreau.tennistour.core.entities;

import java.util.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(Arquillian.class)
public class PlayerEntityTest {
    @Deployment
    public static Archive<?> createDeployment() {

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addPackage(Player.class.getPackage()).addClass(DatasForTests.class)
            .addAsManifestResource("test-persistence.xml", "persistence.xml")
            .addAsManifestResource("wildfly-ds.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "META-INF/beans.xml");
        
        return jar;
    }
    
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserTransaction utx;
 
    @Before
    public void preparePersistenceTest() throws Exception {
        clearData();
        insertData();
        startTransaction();
    }

    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Dumping old records...");
        em.createQuery("delete from Player").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
       
        for (Player p : DatasForTests.getPlayers()) {
            em.persist(p);
        }
        utx.commit();
        // reset the persistence context (cache)
        em.clear();
    }

    private void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }
    
    @After
    public void commitTransaction() throws Exception {
        utx.commit();
    }
    
    @Test
    public void shouldFindAllPlayersUsingJpqlQuery() throws Exception {
        // given
        String fetchingAllPlayersInJpql = "select p from Player p order by p.id";

        // when
        System.out.println("Selecting (using JPQL)...");
        List<Player> players = em.createQuery(fetchingAllPlayersInJpql, Player.class).getResultList();

        // then
        System.out.println("Found " + players.size() + " players (using JPQL):");
        assertContainsAllPlayers(players);
    }
    
    private static void assertContainsAllPlayers(Collection<Player> retrievedPlayers) {
        Assert.assertEquals(DatasForTests.PLAYERS.length, retrievedPlayers.size());
        final Set<String> retrievedPlayersTitles = new HashSet<String>();
        for (Player p : retrievedPlayers) {
            System.out.println("* " + p);
            retrievedPlayersTitles.add(p.getName());
        }
        Assert.assertTrue(retrievedPlayersTitles.containsAll(Arrays.asList(DatasForTests.PLAYERS)));
    }
}
