package dap.spotifyAPI.proxy;

import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;

public interface SpotifyInterface {
    Album getAlbum(String albumId);
    Track getTrack(String trackId);
    Playlist getPlaylist(String playlistId);
}