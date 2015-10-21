package com.mgreau.tennistour.entities;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * @author Maxime Gréau <contact@mgreau.com>
 */
@Stateless
@Named
public class PlayerBean {

	@PersistenceContext
	EntityManager em;

	/**
	 * Get the player from the database or create a new instance with the name
	 * 
	 * @param name
	 * @return a entity
	 */
	public Player getByName(String name) {
		try{
		return em.createNamedQuery("Player.findByName", Player.class)
				.setParameter("name", name).getSingleResult();
		}catch(NoResultException nre){
			return new Player();
		}
	}

	public List<Player> listPlayers() {
		return em.createNamedQuery("Player.findAll", Player.class)
				.getResultList();
	}

	public List<Player> listMenPlayers() {
		return em.createNamedQuery("Player.findBySexe", Player.class)
				.setParameter("sexe", 'M').getResultList();
	}

	public List<Player> listWomenPlayers() {
		return em.createNamedQuery("Player.findBySexe", Player.class)
				.setParameter("sexe", 'F').getResultList();
	}
}
