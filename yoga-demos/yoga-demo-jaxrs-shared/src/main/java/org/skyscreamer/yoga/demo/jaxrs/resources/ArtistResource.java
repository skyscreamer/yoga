package org.skyscreamer.yoga.demo.jaxrs.resources;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Artist;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
@Singleton
@Path("/artist")
public class ArtistResource extends AbstractResource<Artist>
{

    @Inject
    public ArtistResource( GenericDao dao )
    {
        super( dao );
    }
}
