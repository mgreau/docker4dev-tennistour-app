package com.mgreau.tennistour.entities;

import java.util.Date;
import java.util.List;

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
public class MatchEntityTest {
  @Deployment(testable=false)
	public static Archive<?> createDeployment() {

		JavaArchive jar = ShrinkWrap
				.create(JavaArchive.class)
				.addPackage(Match.class.getPackage()).addClass(DatasForTests.class)
				.addAsManifestResource("test-persistence.xml",
						"persistence.xml")
				.addAsManifestResource("wildfly-ds.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

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
    em.createQuery("delete from Match").executeUpdate();
    em.createQuery("delete from Player").executeUpdate();
    em.createQuery("delete from Tournament").executeUpdate();
    utx.commit();
  }

  private void insertData() throws Exception {
    utx.begin();
    em.joinTransaction();
    System.out.println("Inserting records...");

    Tournament tournament = DatasForTests.getTournaments().get(0);
    Player player1 = DatasForTests.getPlayers().get(0);
    Player player2 = DatasForTests.getPlayers().get(1);
    em.persist(player2);
    em.persist(player1);
    em.persist(tournament);

    Match m = new Match();
    m.setPlayer1(player1);
    m.setPlayer2(player2);
    m.setTournament(tournament);
    m.setDate(new Date());
    //m.setId(1);
    m.setRound("Final");
    m.setState("PENDING");
    em.persist(m);

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
	public void shouldFindAllMatchesUsingJpqlQuery() throws Exception {
		// given
		String fetchingAllInJpql = "select m from Match m order by m.id";

		// when
		System.out.println("Selecting (using JPQL)...");
		List<Match> Matches = em.createQuery(
				fetchingAllInJpql, Match.class).getResultList();

		// then
		System.out.println("Found " + Matches.size()
				+ " Matches (using JPQL):");
		Assert.assertTrue("all matches not found", Matches.size()==1);
	}

	
}
