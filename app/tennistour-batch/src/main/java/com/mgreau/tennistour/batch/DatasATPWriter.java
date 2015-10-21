package com.mgreau.tennistour.batch;

import com.mgreau.tennistour.entities.*;

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

      Player p2db = pBean.getByName(((Match) match).getPlayer2().getName());
      if (p2db.getId() != null)
        ((Match) match).setPlayer2(p2db);
			
			Tournament tournament = ((Match) match).getTournament();
			Tournament t = tBean.findByName(tournament.getIdAssociation(), tournament.getYear());
			if (t.getId() != null)
				((Match) match).setTournament(t);
			
			em.persist((Match) match);
		}
	}
}
