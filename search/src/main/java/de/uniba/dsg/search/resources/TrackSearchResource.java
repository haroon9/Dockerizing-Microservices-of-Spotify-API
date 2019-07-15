package de.uniba.dsg.search.resources;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import de.uniba.dsg.search.CustomSpotifyApi;
import de.uniba.dsg.search.exceptions.ClientRequestException;
import de.uniba.dsg.search.exceptions.RemoteApiException;
import de.uniba.dsg.search.exceptions.ResourceNotFoundException;
import de.uniba.dsg.search.interfaces.TrackSearchAPI;
import de.uniba.dsg.search.models.ArtistResponse;
import de.uniba.dsg.search.models.ErrorMessage;
import de.uniba.dsg.search.models.TrackResponse;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Path("tracks")
public class TrackSearchResource implements TrackSearchAPI {

    @Override
    @GET
    @Path("search")
    public TrackResponse searchTrack(@QueryParam("title") String title, @QueryParam("artist") String artist) {

        if (title == null || title.equals("")) {
            throw new ClientRequestException(new ErrorMessage("Required query parameter is missing: title"));
        }

        String query;
        if (artist == null || artist.equals("")) {
            query = "track:" + title;
        } else {
            query = "artist:" + artist + " track:" + title;
        }
        SearchItemRequest searchRequest = CustomSpotifyApi.getInstance().searchItem(query, "track").limit(50).build();

        try {
            SearchResult searchResult = searchRequest.execute();
            Track[] tracks = searchResult.getTracks().getItems();

            Track foundTrack = null;

            if (artist != null) {
                for (Track t : tracks) {
                    // look for match of artist name
                    boolean artistMatch = Arrays.stream(t.getArtists()).anyMatch(a -> a.getName().toLowerCase().equals(artist.toLowerCase()));

                    if (artistMatch) {
                        foundTrack = t;
                        break;
                    }
                }
            }

            // if no exact match was found, use first available result
            if (foundTrack == null && tracks.length > 0) {
                foundTrack = tracks[0];
            }

            // nothing could be found at all
            if (foundTrack == null) {
                throw new ResourceNotFoundException(new ErrorMessage(String.format("No matching track found for title: %s, artist: %s", title, artist)));
            }

            TrackResponse response = new TrackResponse();
            response.setId(foundTrack.getId());
            response.setTitle(foundTrack.getName());

            // join multiple artist names together
            List<String> artistNames = Arrays.stream(foundTrack.getArtists()).map(ArtistSimplified::getName).collect(Collectors.toList());
            String artists = String.join(", ", artistNames);
            response.setArtist(artists);

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

