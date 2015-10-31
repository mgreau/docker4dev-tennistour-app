package com.mgreau.tennistour.batch.jobs;

import com.mgreau.tennistour.core.entities.*;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Named
public class DatasATPWriter extends AbstractItemWriter {

  @PersistenceContext
  EntityManager em;

  @Inject
  PlayerBean pBean;

  @Inject
  TournamentBean tBean;

  @Override
  public void writeItems(List<Object> list) {
    System.out.println("writeItems: " + list);
    for (Object match : list) {
      Player p1db = pBean.getByName(((Match) match).getPlayer1().getName());
      if (p1db.getId() != null)
        ((Match) match).setPlayer1(p1db);
      else {
        if (p1db.getSexe() == null) {
          p1db.setSexe('U');
        }
        if (p1db.getName() == null) {
          p1db.setName("OK");
        }
        em.persist(p1db);

      }

      Player p2db = pBean.getByName(((Match) match).getPlayer2().getName());
      if (p2db.getId() != null)
        ((Match) match).setPlayer2(p2db);
      else
        em.persist(p2db);

      Tournament tournament = ((Match) match).getTournament();
      Tournament t = tBean.findByName(tournament.getIdAssociation(), tournament.getYear());
      if (t.getId() != null)
        ((Match) match).setTournament(t);
      else {
        if (p2db.getSexe() == null) {
          p2db.setSexe('U');
        }
        if (p2db.getName() == null) {
          p2db.setName("OK");
        }
        em.persist(p2db);
        em.persist(tournament);

        em.persist((Match) match);
      }
    }
	}
}
