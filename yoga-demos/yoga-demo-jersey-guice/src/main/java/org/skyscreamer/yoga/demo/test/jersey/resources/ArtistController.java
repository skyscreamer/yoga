package org.skyscreamer.yoga.demo.test.jersey.resources;

import org.skyscreamer.yoga.demo.model.Artist;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */

@Singleton
@Path("/artist")
public class ArtistController extends AbstractController<Artist>
{
    @GET
    public List<Artist> getArtists( @QueryParam("selector") String selectorString )
    {
        return _genericDao.findAll( Artist.class );
    }
}
