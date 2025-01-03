package dap.spotifyAPI.template;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;

import java.util.List;

public abstract class SearchTemplate {
    protected List<Song> _songs;
    protected SpotifyInterface _manager;
    public abstract void apiCall (String name, String searchField);
    public abstract boolean hasSong();
    public abstract void fetchSong();
    public final void Search(SpotifyInterface manager, String name, String searchField) {
        _manager = manager;
        apiCall(name, searchField);
        while (hasSong()) {
            fetchSong();
        }
    }
}
