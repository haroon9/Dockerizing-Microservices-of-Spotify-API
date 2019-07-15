package de.uniba.dsg.images.resources;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.BadRequestException;
import com.wrapper.spotify.exceptions.detailed.NotFoundException;
import com.wrapper.spotify.model_objects.specification.Image;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;
import de.uniba.dsg.images.CustomSpotifyApi;
import de.uniba.dsg.images.models.ImageResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/covers")
@Produces(MediaType.APPLICATION_JSON) // always produce JSON
public class ImagesResource {

    @GET
    @Path("/{track-id}")
    @Produces(MediaType.APPLICATION_JSON) // always produce JSON
    public Response getCoverArt(@PathParam("track-id") String trackId, @QueryParam ("desiredWidth") String desiredWidth) {
        if (trackId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Required query parameter is missing: track-id").build();
        }

        GetTrackRequest request = CustomSpotifyApi.getInstance().getTrack(trackId).build();

        try {
            Track track = request.execute();

            if (track == null) {
                return Response.status(NOT_FOUND).entity(String.format("No matching track found for id: %s", trackId)).build();
            }

            Image[] images = track.getAlbum().getImages();
            if (images.length == 0) {
                return Response.status(NOT_FOUND).entity(String.format("No album images found for id: %s", trackId)).build();
            }

            String url;

            if (desiredWidth != null) {
                try {
                    int desiredWidthNum = Integer.parseInt(desiredWidth);
                    Image closest = null;

                    for (Image i: images) {
                        if (closest == null) {
                            closest = i;
                            continue;
                        }

                        if (Math.abs(i.getWidth() - desiredWidthNum) < Math.abs(closest.getWidth() - desiredWidthNum)) {
                            closest = i;
                        }
                    }

                    url = closest.getUrl();

                } catch (NumberFormatException e) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Bad parameter 'desiredWidth': Not an integer").build();
                }

            } else {
                int middleIndex = ((int) Math.floor(images.length / 2));
                url = images[middleIndex].getUrl();
            }

            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setUrl(url);

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(imageResponse);
            return Response.ok(json).build();

        } catch (NotFoundException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SpotifyWebApiException | IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
