package dap.spotifyAPI.products.pop;

import dap.spotifyAPI.utils.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.michaelthelin.spotify.SpotifyApi;

import java.util.List;

/**
 * Clase abstracta que define el comportamiento de los productos de tipo Pop.
 */
public abstract class PopProduct {
    /// Objeto que se encarga de registrar los eventos de la clase PopProduct.
    protected Logger _logger = LoggerFactory.getLogger(PopProduct.class);
    /// Lista de canciones que se van a crear en la playlist.
    public abstract List<Song> create(SpotifyApi spotifyApi, int amount);
}