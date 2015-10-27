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
public class TournamentEntityTest {
	@Deployment
	public static Archive<?> createDeployment() {

		JavaArchive jar = ShrinkWrap
				.create(JavaArchive.class)
				.addPackage(Tournament.class.getPackage()).addClass(DatasForTests.class)
				.addAsManifestResource("test-persistence.xml",
						"persistence.xml")
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
		em.createQuery("delete from Tournament").executeUpdate();
		utx.commit();
	}

	private void insertData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Inserting records...");

		for (Tournament t : DatasForTests.getTournaments())
			em.persist(t);
		
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
	public void shouldFindAllTournamentsUsingJpqlQuery() throws Exception {
		// given
		String fetchingAllTournamentsInJpql = "select t from Tournament t order by t.id";

		// when
		System.out.println("Selecting (using JPQL)...");
		List<Tournament> Tournaments = em.createQuery(
				fetchingAllTournamentsInJpql, Tournament.class).getResultList();

		// then
		System.out.println("Found " + Tournaments.size()
				+ " Tournaments (using JPQL):");
		assertContainsAllTournaments(Tournaments);
	}

	private static void assertContainsAllTournaments(
			Collection<Tournament> retrievedTournaments) {
		Assert.assertEquals(DatasForTests.TOURNAMENTS.length, retrievedTournaments.size());
		final Set<String> retrievedTournamentsTitles = new HashSet<String>();
		for (Tournament t : retrievedTournaments) {
			System.out.println("* " + t);
			retrievedTournamentsTitles.add(t.getName());
		}
		Assert.assertTrue(retrievedTournamentsTitles.containsAll(Arrays
				.asList(DatasForTests.TOURNAMENTS)));
	}
}
