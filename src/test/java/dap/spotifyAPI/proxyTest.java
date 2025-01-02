package dap.spotifyAPI;

import dap.spotifyAPI.proxy.Proxy;
import dap.spotifyAPI.proxy.Spotify;
import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

import java.util.List;

public class proxyTest {
    public static void main(String[] args) {
        String client_id = "75399c2bdb7948b882f6647795204070";
        String client_secret = "484b0ac5151f4288a40c06e8ae7a4dc4";

        SpotifyInterface spotify = new Spotify(client_id, client_secret);
        SpotifyInterface manager = new Proxy(spotify);

        // https://open.spotify.com/intl-es/artist/4q3ewBCX7sLwd24euuV69X?si=92ec8e434e234049
//        String artistId = "Bad Bunny";
//        List<AlbumSimplified> t = manager.getAlbumsByArtist(artistId);
//        for (AlbumSimplified album : t) {
//            System.out.println("Album name: " + album.getName());
//        }
//        String id = t.get(0).getId();
//        for (Song t2 : manager.getAlbumTracks(id)) {
//            System.out.println("Track name: " + t2.name);
//        }
        String playlistId = "4aao0tTCslyiYQqBkh5K1g";
        int i = 0;
        for (Song t : manager.getPlaylistTracks(playlistId)) {
            System.out.println(i++ + "Track name: " + t.name);
        }
    }
}
