package dap.spotifyAPI.proxy;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;

public class Spotify implements SpotifyInterface {
    private final SpotifyApi _spotifyApi;
    public Spotify(String id, String secret) {
        this._spotifyApi = new SpotifyApi.Builder()
                .setClientId(id)
                .setClientSecret(secret)
                .build();
        try {
            String accessToken = _spotifyApi.clientCredentials().build().execute().getAccessToken();
            _spotifyApi.setAccessToken(accessToken);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    @Override
    public Album getAlbum(String albumId) {
        try {
            return _spotifyApi.getAlbum(albumId).build().execute();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Track getTrack(String trackId) {
        try {
            return _spotifyApi.getTrack(trackId).build().execute();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Playlist getPlaylist(String playlistId) {
        try {
            return _spotifyApi.getPlaylist(playlistId).build().execute();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
