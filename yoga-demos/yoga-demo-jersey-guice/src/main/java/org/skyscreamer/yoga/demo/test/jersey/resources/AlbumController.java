package org.skyscreamer.yoga.demo.test.jersey.resources;

import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.model.Album;

@Singleton
@Path("/album")
public class AlbumController extends AbstractController<Album>
{
}
