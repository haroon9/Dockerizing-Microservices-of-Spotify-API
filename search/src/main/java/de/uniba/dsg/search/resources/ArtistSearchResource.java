package de.uniba.dsg.search.resources;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import de.uniba.dsg.search.CustomSpotifyApi;
import de.uniba.dsg.search.exceptions.ClientRequestException;
import de.uniba.dsg.search.exceptions.RemoteApiException;
import de.uniba.dsg.search.exceptions.ResourceNotFoundException;
import de.uniba.dsg.search.interfaces.ArtistSearchAPI;
import de.uniba.dsg.search.models.ArtistResponse;
import de.uniba.dsg.search.models.ErrorMessage;

import javax.ws.rs.*;
import java.io.IOException;

@Path("artists")
public class ArtistSearchResource implements ArtistSearchAPI {

    @Override
    @GET
    @Path("search")
    public ArtistResponse searchArtist(@QueryParam("name") String artistName) {
        if (artistName == null) {
            throw new ClientRequestException(new ErrorMessage("Required query parameter is missing: name"));
        } else if (artistName.equals("")) {
            throw new ClientRequestException(new ErrorMessage("'name' parameter is empty"));
        }

        SearchArtistsRequest artistRequest = CustomSpotifyApi.getInstance().searchArtists(artistName).limit(1).build();

        try {
            // get search results
            Paging<Artist> artistSearchResult = artistRequest.execute();
            Artist[] artists = artistSearchResult.getItems();

            if (artists == null) {
                throw new ResourceNotFoundException(new ErrorMessage(String.format("No matching artist found for query: %s", artistName)));
            }

            if (artists.length == 0) {
                throw new ResourceNotFoundException(new ErrorMessage(String.format("No matching artist found for query: %s", artistName)));
            }

            Artist artist = artists[0];
            ArtistResponse response = new ArtistResponse();
            response.setId(artist.getId());
            response.setName(artist.getName());

            return response;
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(new ErrorMessage(e.getMessage()));
        } catch (BadRequestException e) {
            throw new ClientRequestException(new ErrorMessage(e.getMessage()));
        } catch (SpotifyWebApiException | IOException e) {
            throw new RemoteApiException(new ErrorMessage(e.getMessage()));
        }
    }
}
