package com.mgreau.tennistour.core.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PLAYER", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME" }))
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
      @NamedQuery(name = "Player.findById", query = "SELECT p FROM Player p WHERE p.id = :id"),
      @NamedQuery(name = "Player.findBySexe", query = "SELECT p FROM Player p WHERE p.sexe = :sexe"),
      @NamedQuery(name = "Player.findByName", query = "SELECT p FROM Player p WHERE p.name = :name") })
public class Player implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Id
   @Basic(optional = false)
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 50)
   private String name;

   @Basic(optional = false)
   @NotNull
   private Character sexe;

   public Player()
   {

   }

   public Integer getId()
   {
      return id;
   }

   public void setId(Integer id)
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Character getSexe()
   {
      return sexe;
   }

   public void setSexe(Character sexe)
   {
      this.sexe = sexe;
   }

   @Override
   public int hashCode()
   {
      int hash = 0;
      hash += (id != null ? id.hashCode() : 0);
      return hash;
   }

   @Override
   public boolean equals(Object object)
   {
      if (!(object instanceof Player))
      {
         return false;
      }
      Player other = (Player) object;
      if ((this.id == null && other.id != null)
            || (this.id != null && !this.id.equals(other.id)))
      {
         return false;
      }
      else if (this.id == null && other.id == null)
      {
         if (this.name != null && other.name != null
               && this.name.equals(other.name))
            return true;
      }
      return false;
   }

   @Override
   public String toString()
   {
      return "Player@" + hashCode() + "[id = " + id + "; name = " + name
            + "]";
   }

}
