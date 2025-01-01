package dap.spotifyAPI.proxy;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.*;

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

    @Override
    public User getUser(String userId) {
        try {
            return _spotifyApi.getUsersProfile(userId).build().execute();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Artist getArtist(String artistId) {
        try {
            return _spotifyApi.getArtist(artistId).build().execute();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
