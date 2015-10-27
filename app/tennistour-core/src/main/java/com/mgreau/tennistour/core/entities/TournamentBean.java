package com.mgreau.tennistour.core.entities;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
@Named
public class TournamentBean {

	@PersistenceContext(unitName = "tennistourPU")
	EntityManager em;

	public Tournament findByName(Integer idAssociation, Integer year) {
		try {
			return em
					.createNamedQuery("Tournament.findByIdAssocAndYear",
							Tournament.class)
					.setParameter("idAssoc", idAssociation)
					.setParameter("year", year).getSingleResult();
		} catch (NoResultException nre) {
			return new Tournament();
		}
	}
	
	public List<Tournament> listTournaments() {
		return em.createNamedQuery("Tournament.findAll", Tournament.class)
				.getResultList();
	}

}
