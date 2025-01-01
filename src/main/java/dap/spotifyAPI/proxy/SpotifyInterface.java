package dap.spotifyAPI.proxy;

import se.michaelthelin.spotify.model_objects.specification.*;

public interface SpotifyInterface {
    Album getAlbum(String albumId);
    Track getTrack(String trackId);
    Playlist getPlaylist(String playlistId);
    User getUser(String userId);
    Artist getArtist(String artistId);
}