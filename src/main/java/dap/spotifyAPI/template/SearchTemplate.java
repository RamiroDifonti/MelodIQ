package dap.spotifyAPI.template;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SearchTemplate {
    protected List<Song> _songs = new ArrayList<>();
    protected SpotifyInterface _manager;
    protected JPanel _panel;
    protected abstract void apiCall (String name, String searchField);
    protected abstract boolean hasSong();
    protected abstract void fetchSong();
    public final JPanel Search(SpotifyInterface manager, String name, String searchField) {
        _manager = manager;
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.Y_AXIS));

        apiCall(name, searchField);
        if (hasSong()) {
            while (hasSong()) {
                fetchSong();
            }
            return _panel;
        } else {
            JOptionPane.showMessageDialog(_panel.getParent(), "Error 404: No encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
