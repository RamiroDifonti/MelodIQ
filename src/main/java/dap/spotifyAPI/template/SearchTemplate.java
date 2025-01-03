package dap.spotifyAPI.template;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;

import javax.swing.*;
import java.util.List;

public abstract class SearchTemplate {
    protected List<Song> _songs;
    protected SpotifyInterface _manager;
    protected JScrollPane _scrollpane;
    protected abstract void apiCall (String name, String searchField);
    protected abstract boolean hasSong();
    protected abstract void fetchSong();
    public final JScrollPane Search(SpotifyInterface manager, String name, String searchField) {
        _manager = manager;
        _scrollpane = new JScrollPane();
        apiCall(name, searchField);
        while (hasSong()) {
            fetchSong();
        }
        return _scrollpane;
    }
}
