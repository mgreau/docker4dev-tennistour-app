package com.mgreau.tennistour.rest.api;

import com.mgreau.tennistour.entities.Player;

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
@Path("/players")
public class PlayerEndpoint
{
   @PersistenceContext(unitName = "tennistour")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Player entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(PlayerEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Integer id)
   {
      Player entity = em.find(Player.class, id);
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
      TypedQuery<Player> findByIdQuery = em.createQuery("SELECT p FROM Player p WHERE p.id = :entityId", Player.class);
      findByIdQuery.setParameter("entityId", id);
      Player entity = findByIdQuery.getSingleResult();
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<Player> listAll()
   {
      final List<Player> results = em.createQuery("SELECT p FROM Player p", Player.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Integer id, Player entity)
   {
      entity.setId(id);
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}
