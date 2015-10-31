package com.mgreau.tennistour.core.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "MATCHES")
@XmlRootElement
public class Match implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Temporal(TemporalType.DATE)
  private Date matchDate;

  @ManyToOne( cascade = CascadeType.ALL )
  @JoinColumn(name = "PLAYER1_ID", nullable = false)
  private Player player1;

  @ManyToOne( cascade = CascadeType.ALL )
  @JoinColumn(name = "PLAYER2_ID", nullable = false)
  private Player player2;

  @ManyToOne( cascade = CascadeType.ALL )
  @JoinColumn(name = "TOURNAMENT_ID", nullable = false)
  private Tournament tournament;

  private String round;

  // Player 1
  private int p1Sets;

  private int p1Set1;

  private int p1Set2;

  private int p1Set3;

  private int p1Set4;

  private int p1Set5;

  // Player2
  private int p2Sets;

  private int p2Set1;

  private int p2Set2;

  private int p2Set3;

  private int p2Set4;

  private int p2Set5;

  private Integer winner;

  private Integer loser;

  /** COMPLETED, WALKOVER */
  private String comment;

  /** PENDING, FINISHED, LIVE */
  private String state;

  public Match() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Player getPlayer1() {
    return player1;
  }

  public void setPlayer1(Player player1) {
    this.player1 = player1;
  }

  public Player getPlayer2() {
    return player2;
  }

  public void setPlayer2(Player player2) {
    this.player2 = player2;
  }

  public Tournament getTournament() {
    return tournament;
  }

  public void setTournament(Tournament tournament) {
    this.tournament = tournament;
  }

  public void setWinner(Integer winner) {
    this.winner = winner;
  }

  public void setLoser(Integer loser) {
    this.loser = loser;
  }

  public Date getMatchDate() {
    return matchDate;
  }

  public void setMatchDate(Date matchDate) {
    this.matchDate = matchDate;
  }

  public String getRound() {
    return round;
  }

  public void setRound(String round) {
    this.round = round;
  }

  public int getP1Sets() {
    return p1Sets;
  }

  public void setP1Sets(int p1Sets) {
    this.p1Sets = p1Sets;
  }

  public int getP1Set1() {
    return p1Set1;
  }

  public void setP1Set1(int p1Set1) {
    this.p1Set1 = p1Set1;
  }

  public int getP1Set2() {
    return p1Set2;
  }

  public void setP1Set2(int p1Set2) {
    this.p1Set2 = p1Set2;
  }

  public int getP1Set3() {
    return p1Set3;
  }

  public void setP1Set3(int p1Set3) {
    this.p1Set3 = p1Set3;
  }

  public int getP1Set4() {
    return p1Set4;
  }

  public void setP1Set4(int p1Set4) {
    this.p1Set4 = p1Set4;
  }

  public int getP1Set5() {
    return p1Set5;
  }

  public void setP1Set5(int p1Set5) {
    this.p1Set5 = p1Set5;
  }

  public int getP2Sets() {
    return p2Sets;
  }

  public void setP2Sets(int p2Sets) {
    this.p2Sets = p2Sets;
  }

  public int getP2Set1() {
    return p2Set1;
  }

  public void setP2Set1(int p2Set1) {
    this.p2Set1 = p2Set1;
  }

  public int getP2Set2() {
    return p2Set2;
  }

  public void setP2Set2(int p2Set2) {
    this.p2Set2 = p2Set2;
  }

  public int getP2Set3() {
    return p2Set3;
  }

  public void setP2Set3(int p2Set3) {
    this.p2Set3 = p2Set3;
  }

  public int getP2Set4() {
    return p2Set4;
  }

  public void setP2Set4(int p2Set4) {
    this.p2Set4 = p2Set4;
  }

  public int getP2Set5() {
    return p2Set5;
  }

  public void setP2Set5(int p2Set5) {
    this.p2Set5 = p2Set5;
  }

  public Integer getWinner() {
    return winner;
  }

  public Integer getLoser() {
    return loser;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

}
