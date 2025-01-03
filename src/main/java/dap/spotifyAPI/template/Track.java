package dap.spotifyAPI.template;

import dap.spotifyAPI.utils.Song;

import javax.swing.*;
import java.util.List;

public class Track extends SearchTemplate {
    @Override
    protected void apiCall(String name, String searchField) {
        List<Song> songs = _manager.getTracksByArtist(name);
        for (Song song : songs) {
            if (song.getName().contains(searchField)) {
                System.out.println("Found song: " + song.getName());
                _songs.add(song);
                break;
            }
        }
    }

    @Override
    protected boolean hasSong() {
        if (_songs == null) {
            JOptionPane.showMessageDialog(_panel.getParent(), "Canción no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return !_songs.isEmpty();
    }

    @Override
    protected void fetchSong() {
        Song song = _songs.get(0);
        _songs.remove(0);
        _panel.add(song.getLayout());
    }
}
