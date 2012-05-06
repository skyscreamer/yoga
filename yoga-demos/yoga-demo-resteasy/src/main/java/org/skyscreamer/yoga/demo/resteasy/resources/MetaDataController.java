package org.skyscreamer.yoga.demo.resteasy.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.skyscreamer.yoga.metadata.MetaDataService;
import org.skyscreamer.yoga.metadata.TypeMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Path("/metadata/{type}")
public class MetaDataController {
	@Autowired
	MetaDataService metaDataService;

	@GET
	public TypeMetaData getTypeMetaData(@PathParam("type") String type, @Context HttpServletRequest request) {
		String uri = request.getRequestURI();
		String parts[] = uri.split("\\.");
		return metaDataService.getMetaData(type, parts[parts.length - 1]);
	}

}
