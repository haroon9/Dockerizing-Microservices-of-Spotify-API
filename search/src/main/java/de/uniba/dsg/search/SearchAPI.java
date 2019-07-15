package de.uniba.dsg.search;

import de.uniba.dsg.search.resources.ArtistSearchResource;
import de.uniba.dsg.search.resources.TrackSearchResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/") // bind to root path
@Consumes(MediaType.APPLICATION_JSON) // makes API always consume JSON
@Produces(MediaType.APPLICATION_JSON) // always produce JSON
public class SearchAPI extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(ArtistSearchResource.class);
        resources.add(TrackSearchResource.class);
        resources.add(DebugMapper.class);
        return resources;
    }
}
