package com.mgreau.tennistour.batch.jobs;

import com.mgreau.tennistour.core.entities.*;

import java.util.List;
import java.util.logging.Logger;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Named
public class DatasATPWriter extends AbstractItemWriter {

  private final Logger logger = Logger.getLogger(this.getClass().getName());

  @PersistenceContext
  EntityManager em;

  @Inject
  PlayerBean pBean;

  @Inject
  TournamentBean tBean;

  @Override
  public void writeItems(List<Object> list) {
    for (Object match : list) {
      logger.info("Match: " + ((Match) match).toString());

      Player player1 = ((Match) match).getPlayer1();
      Player p1db = pBean.getByName(player1.getName());
      if (p1db.getId() != null) {
        ((Match) match).setPlayer1(p1db);
        ((Match) match).setWinner(p1db.getId());
      }
      else {
        em.persist(player1);
        ((Match) match).setWinner(player1.getId());
      }

      Player player2 = ((Match) match).getPlayer2();
      Player p2db = pBean.getByName(player2.getName());
      if (p2db.getId() != null) {
        ((Match) match).setPlayer2(p2db);
        ((Match) match).setLoser(p2db.getId());
      }
      else {
        em.persist(player2);
        ((Match) match).setLoser(player2.getId());
      }

      Tournament tournament = ((Match) match).getTournament();
      Tournament t = tBean.findByName(tournament.getIdAssociation(), tournament.getYear());
      if (t.getId() != null)
        ((Match) match).setTournament(t);
      else {
        em.persist(tournament);
      }
      em.persist(((Match) match));
    }
	}
}
