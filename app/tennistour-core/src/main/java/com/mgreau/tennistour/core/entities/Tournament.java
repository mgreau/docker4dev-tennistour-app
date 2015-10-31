package com.mgreau.tennistour.core.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
//@Table(name = "TOURNAMENT")
@Table(name = "TOURNAMENT", uniqueConstraints = @UniqueConstraint(columnNames = { "ID_ASSOCIATION", "YEAR" }))
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "Tournament.findAll", query = "SELECT t FROM Tournament t"),
      @NamedQuery(name = "Tournament.findByIdAssocAndYear", query = "SELECT t FROM Tournament t WHERE t.idAssociation = :idAssoc AND t.year = :year") })
public class Tournament implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Id
   @Basic(optional = false)
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   /** ID ATP/WTA */
   @Basic(optional = false)
   @NotNull
   @Column(name = "ID_ASSOCIATION")
   private Integer idAssociation;

   /** ATP or WTA */
   @Basic(optional = false)
   @NotNull
   @Size(min = 3, max = 3)
   @Column(name = "TYPE_ASSOCIATION")
   private String typeAssociation;

   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 50)
   private String location;

   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 50)
   private String name;

   @Basic(optional = false)
   @NotNull
   @Min(1950)
   @Max(2100)
   @Digits(integer = 4, fraction = 0)
   private Integer year;

   /**
    * Type of court (outdoors or indoors)
    */
   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 50)
   private String court;

   /**
    * Type of surface (clay, hard, carpet or grass)
    */
   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 50)
   private String surface;

   @Basic(optional = false)
   @NotNull
   @Min(3)
   @Max(5)
   @Digits(integer = 1, fraction = 0)
   @Column(name = "BEST_OF_SET")
   private Integer bestOfSet;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
   private Collection<Match> matches;

   public Tournament()
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

   public Integer getIdAssociation()
   {
      return idAssociation;
   }

   public void setIdAssociation(Integer idAssociation)
   {
      this.idAssociation = idAssociation;
   }

   public String getTypeAssociation()
   {
      return typeAssociation;
   }

   public void setTypeAssociation(String typeAssociation)
   {
      this.typeAssociation = typeAssociation;
   }

   public String getLocation()
   {
      return location;
   }

   public void setLocation(String location)
   {
      this.location = location;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Integer getYear()
   {
      return year;
   }

   public void setYear(Integer year)
   {
      this.year = year;
   }

   public String getCourt()
   {
      return court;
   }

   public void setCourt(String court)
   {
      this.court = court;
   }

   public String getSurface()
   {
      return surface;
   }

   public void setSurface(String surface)
   {
      this.surface = surface;
   }

   public Integer getBestOfSet()
   {
      return bestOfSet;
   }

   public void setBestOfSet(Integer bestOfSet)
   {
      this.bestOfSet = bestOfSet;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result
            + ((idAssociation == null) ? 0 : idAssociation.hashCode());
      result = prime * result + ((year == null) ? 0 : year.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Tournament other = (Tournament) obj;
      if (id == null)
      {
         if (other.id != null)
            return false;
      }
      else if (!id.equals(other.id))
         return false;
      if (idAssociation == null)
      {
         if (other.idAssociation != null)
            return false;
      }
      else if (!idAssociation.equals(other.idAssociation))
         return false;
      if (year == null)
      {
         if (other.year != null)
            return false;
      }
      else if (!year.equals(other.year))
         return false;
      return true;
   }

}
