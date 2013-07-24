package org.skyscreamer.yoga.demo.jersey.resources;

import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.model.Song;

@Singleton
@Path("/song")
public class SongController extends AbstractController<Song>
{
}
