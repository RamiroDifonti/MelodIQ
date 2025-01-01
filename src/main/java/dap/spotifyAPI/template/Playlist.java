package dap.spotifyAPI.template;

import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void fetchSong() {
        // TODO Auto-generated method stub
    }
}
