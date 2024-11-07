package dap.spotifyAPI.products.reggaetton;

import se.michaelthelin.spotify.SpotifyApi;
import utils.Song;

import java.util.List;

public abstract class ReggaettonProduct {
    public abstract List<Song> create(SpotifyApi spotifyApi, int amount);
}
