package dap.spotifyAPI.template;

import dap.spotifyAPI.proxy.Spotify;
import dap.spotifyAPI.proxy.SpotifyInterface;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.List;

public abstract class SearchTemplate {
    protected List<Track> _songs;
    protected SpotifyInterface _manager;
    public abstract void apiCall (String name, String searchField);
    public abstract boolean hasSong();
    public abstract void fetchSong();
    public final void Search(SpotifyInterface manager, String name, String searchField) {
        _manager = manager;
        apiCall(name, searchField);
        if (hasSong()) {
            fetchSong();
        }
    }
}
