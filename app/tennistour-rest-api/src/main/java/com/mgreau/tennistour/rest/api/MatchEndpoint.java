package com.mgreau.tennistour.rest.api;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.mgreau.tennistour.entities.Match;

/**
 * 
 */
@Stateless
@Path("/matchs")
public class MatchEndpoint
{
   @PersistenceContext(unitName = "tennistour")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Match entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(MatchEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Integer id)
   {
      Match entity = em.find(Match.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Integer id)
   {
      TypedQuery<Match> findByIdQuery = em.createQuery("SELECT m FROM Match m LEFT JOIN FETCH m.player1 LEFT JOIN FETCH m.player2 LEFT JOIN FETCH m.tournament WHERE m.id = :entityId", Match.class);
      findByIdQuery.setParameter("entityId", id);
      Match entity = findByIdQuery.getSingleResult();
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<Match> listAll()
   {
      final List<Match> results = em.createQuery("SELECT m FROM Match m LEFT JOIN FETCH m.player1 LEFT JOIN FETCH m.player2 LEFT JOIN FETCH m.tournament", Match.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Integer id, Match entity)
   {
      entity.setId(id);
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}
