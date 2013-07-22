package org.skyscreamer.yoga.demo.resteasy.resources;

import org.skyscreamer.yoga.demo.model.Artist;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
@Path("/artist")
public class ArtistController extends AbstractController<Artist>
{
    @GET
    public List<Artist> getArtists( @QueryParam("selector") String selectorString )
    {
        return _genericDao.findAll( Artist.class );
    }
}
