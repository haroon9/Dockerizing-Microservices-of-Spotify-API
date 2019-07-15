package de.uniba.dsg.search.interfaces;

import de.uniba.dsg.search.models.ArtistResponse;

public interface ArtistSearchAPI {

    ArtistResponse searchArtist(String artistName);
}
