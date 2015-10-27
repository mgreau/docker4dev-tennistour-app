package com.mgreau.tennistour.core.rest.api;

import com.mgreau.tennistour.core.entities.Tournament;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;


/**
 * 
 */
@Stateless
@Path("/tournaments")
public class TournamentEndpoint
{
   @PersistenceContext(unitName = "tennistour")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Tournament entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(TournamentEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Integer id)
   {
      Tournament entity = em.find(Tournament.class, id);
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
      TypedQuery<Tournament> findByIdQuery = em.createQuery("SELECT t FROM Tournament t LEFT JOIN FETCH t.matches WHERE t.id = :entityId", Tournament.class);
      findByIdQuery.setParameter("entityId", id);
      Tournament entity = findByIdQuery.getSingleResult();
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<Tournament> listAll()
   {
      final List<Tournament> results = em.createQuery("SELECT t FROM Tournament t LEFT JOIN FETCH t.matches", Tournament.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Integer id, Tournament entity)
   {
      entity.setId(id);
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}
