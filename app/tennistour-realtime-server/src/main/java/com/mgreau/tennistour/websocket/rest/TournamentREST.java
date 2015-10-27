package com.mgreau.tennistour.websocket.rest;

import java.util.Collection;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mgreau.tennistour.core.TennisMatch;
import com.mgreau.tennistour.websocket.StarterService;

@Path("tournament")
public class TournamentREST {

	@Inject
	StarterService ejb;

	private static final Logger logger = Logger.getLogger("TournamentREST");

	@GET
	@Path("lives")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<TennisMatch> getMatchesOnLive() throws Exception {

		return ejb.getAllMatchesFromCache();
	}

}
