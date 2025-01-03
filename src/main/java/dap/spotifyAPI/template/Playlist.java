package dap.spotifyAPI.template;

import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.List;

public class Playlist extends SearchTemplate {
    @Override
    public void apiCall(String name, String searchField) {
        List<PlaylistSimplified> playlists = _manager.getPlaylistsByUser(name);
        for (PlaylistSimplified playlist : playlists) {
            if (playlist.getName().contains(searchField)) {
                _songs = _manager.getPlaylistTracks(playlist.getId());
                break;
            }
        }
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
