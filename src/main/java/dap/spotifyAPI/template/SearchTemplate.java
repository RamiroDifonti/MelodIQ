package dap.spotifyAPI.template;

import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.List;

public abstract class SearchTemplate {
    protected List<Track> _songs;
    public abstract void apiCall();
    public abstract boolean hasSong();
    public abstract void fetchSong();
    public final void Search() {
        apiCall();
        if (hasSong()) {
            fetchSong();
        }
    }
}
