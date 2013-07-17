package org.skyscreamer.yoga.demo.jersey.resources;

import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.model.Song;

@Path("/song")
public class SongController extends AbstractController<Song>
{
}
