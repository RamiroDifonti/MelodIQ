package dap.spotifyAPI.products.reggaeton;

import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.SpotifyApi;

import java.util.List;

public abstract class ReggaetonProduct {
    public abstract List<Song> create(SpotifyApi spotifyApi, int amount);
}
