package org.skyscreamer.yoga.demo.test.jersey.standalone.resources;

import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Artist;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
@Path("/artist")
public class ArtistResource extends AbstractResource<Artist>
{

    public ArtistResource( GenericDao dao )
    {
        super( dao );
    }
}
