package org.skyscreamer.yoga.demo.jaxrs.resources;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.demo.model.Song;

/**
 * Created by IntelliJ IDEA. User: Carter Page
 */
@Singleton
@Path("/song")
public class SongResource extends AbstractResource<Song>
{

    @Inject
    public SongResource( GenericDao dao )
    {
        super( dao );
    }
}
