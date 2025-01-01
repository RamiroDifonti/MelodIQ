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
        return true;
       // return _songs.isEmpty();
    }

    @Override
    public void fetchSong() {
        Track track = _songs.get(0);
        System.out.println("entra2");
        _songs.remove(0);
        Song song = new Song(track);
        System.out.println("Song: " + track.getName());
    }
}
