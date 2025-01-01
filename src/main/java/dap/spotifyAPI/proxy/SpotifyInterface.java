package dap.spotifyAPI.proxy;

import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.List;

public interface SpotifyInterface {
    List<AlbumSimplified> getAlbumsByArtist(String artistId);
    Track getTrackByArtist(String artistId);
    List<PlaylistSimplified> getPlaylistsByUser(String userId);
}