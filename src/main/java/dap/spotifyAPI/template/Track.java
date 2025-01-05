package dap.spotifyAPI.template;

import dap.spotifyAPI.utils.Song;

import java.util.List;

public class Track extends SearchTemplate {
    @Override
    protected void apiCall(String name, String searchField) {
        List<Song> songs = _manager.getTracksByArtist(name);
        for (Song song : songs) {
            System.out.println("Found song: " + song.getName());
            if (song.getName().contains(searchField)) {
                System.out.println("Found song: " + song.getName());
                _songs.add(song);
                break;
            }
        }
    }

    @Override
    protected boolean hasSong() {
        return !_songs.isEmpty();
    }

    @Override
    protected void fetchSong() {
        Song song = _songs.get(0);
        _songs.remove(0);
        _panel.add(song.getLayout());
    }
}
