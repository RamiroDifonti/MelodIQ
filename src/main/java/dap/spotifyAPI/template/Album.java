package dap.spotifyAPI.template;

import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

import java.util.List;

public class Album extends SearchTemplate {
    @Override
    protected void apiCall(String name, String searchField) {
        List<AlbumSimplified> albums = _manager.getAlbumsByArtist(name);
        for (AlbumSimplified album : albums) {
            if (album.getName().contains(searchField)) {
                _songs = _manager.getAlbumTracks(album.getId());
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
