package org.skyscreamer.yoga.demo.test.jersey.resources;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Singleton
@Path("/it")
public class GuiceResource {
	@GET
	@Produces("application/json")
	public String getIt() {
		return "{\"hello\": \"world\"}";
	}
}
