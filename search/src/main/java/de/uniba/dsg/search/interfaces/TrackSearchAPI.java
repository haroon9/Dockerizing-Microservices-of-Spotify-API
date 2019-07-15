package de.uniba.dsg.search.interfaces;

import de.uniba.dsg.search.models.TrackResponse;

public interface TrackSearchAPI {

    TrackResponse searchTrack(String title, String artist);
}
