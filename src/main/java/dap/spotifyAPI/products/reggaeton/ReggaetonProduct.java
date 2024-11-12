package dap.spotifyAPI.products.reggaeton;

import dap.spotifyAPI.utils.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.michaelthelin.spotify.SpotifyApi;

import java.util.List;

/**
 * Clase abstracta que representa un producto de reggaetón.
 *
 * Esta clase sirve como base para los diferentes tipos de productos de reggaetón,
 * como playlists o géneros específicos, y define el método {@link #create(SpotifyApi, int)}
 * que debe ser implementado por las clases hijas para crear listas de canciones de reggaetón.
 */
public abstract class ReggaetonProduct {

    /**
     * Instancia del logger utilizada para registrar mensajes de log.
     * Esta instancia se utiliza para registrar información sobre el comportamiento de las clases hijas.
     */
    protected static Logger _logger = LoggerFactory.getLogger(ReggaetonProduct.class);

    /**
     * Método abstracto que debe ser implementado por las clases hijas para crear una lista de canciones.
     *
     * @param spotifyApi La instancia de la API de Spotify utilizada para realizar la búsqueda de canciones.
     * @param amount El número de canciones a obtener.
     * @return Una lista de objetos {@link Song} que representan las canciones obtenidas.
     */
    public abstract List<Song> create(SpotifyApi spotifyApi, int amount);
}

