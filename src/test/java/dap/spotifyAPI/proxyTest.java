package dap.spotifyAPI;

import dap.spotifyAPI.proxy.Proxy;
import dap.spotifyAPI.proxy.Spotify;
import dap.spotifyAPI.proxy.SpotifyInterface;

public class proxyTest {
    public static void main(String[] args) {
        String client_id = "75399c2bdb7948b882f6647795204070";
        String client_secret = "484b0ac5151f4288a40c06e8ae7a4dc4";

        SpotifyInterface spotify = new Spotify(client_id, client_secret);
        SpotifyInterface manager = new Proxy(spotify);

        String artistId = "grela2235";
        System.out.println("Track name: " + manager.getPlaylistsByUser(artistId));
    }
}
