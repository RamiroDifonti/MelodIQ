package dap.spotifyAPI.template;

import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.List;

public class Playlist extends SearchTemplate {
    @Override
    protected void apiCall(String name, String searchField) {
        List<PlaylistSimplified> playlists = _manager.getPlaylistsByUser(name);
        for (PlaylistSimplified playlist : playlists) {
            if (playlist.getName().contains(searchField)) {
                _songs = _manager.getPlaylistTracks(playlist.getId());
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
