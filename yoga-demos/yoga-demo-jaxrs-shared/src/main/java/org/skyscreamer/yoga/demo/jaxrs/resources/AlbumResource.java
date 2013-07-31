package org.skyscreamer.yoga.demo.jaxrs.resources;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Album;

/**
 * Created by IntelliJ IDEA. User: Carter Page Date: 4/19/11 Time: 6:06 PM
 */
@Singleton
@Path("/album")
public class AlbumResource extends AbstractResource<Album>
{

    @Inject
    public AlbumResource( GenericDao dao )
    {
        super( dao );
    }
}
