package org.skyscreamer.yoga.demo.test.jersey.standalone.resources;

import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Song;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
@Path("/song")
public class SongResource extends AbstractResource<Song>
{

    public SongResource( GenericDao dao )
    {
        super( dao );
    }
}
