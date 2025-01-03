package dap.spotifyAPI.template;

import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

import java.util.List;

public class Track extends SearchTemplate {
    @Override
    public void apiCall(String name, String searchField) {
        List<Song> songs = _manager.getTracksByArtist(name);
        for (Song song : songs) {
            if (song.getName().contains(searchField)) {
                _songs.add(song);
                break;
            }
        }
        _songs = _manager.getTracksByArtist(name);
    }

    @Override
    public boolean hasSong() {
        return !_songs.isEmpty();
    }

    @Override
    public void fetchSong() {
        Song song = _songs.get(0);
        _songs.remove(0);
        System.out.println("Song: " + song.getName());
    }
}
