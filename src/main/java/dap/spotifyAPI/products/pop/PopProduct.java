package dap.spotifyAPI.products.pop;

import dap.spotifyAPI.utils.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.michaelthelin.spotify.SpotifyApi;

import java.util.List;

/**
 * Clase abstracta que define el comportamiento de los productos de tipo Pop.
 *
 * Esta clase sirve como base para otros productos relacionados con el género Pop.
 * Define un método abstracto {@link #create(SpotifyApi, int)} que debe ser implementado
 * por las clases derivadas para crear listas de canciones basadas en el género Pop.
 */
public abstract class PopProduct {

    /**
     * Objeto que se encarga de registrar los eventos de la clase PopProduct.
     *
     * Este logger se utiliza para registrar información y errores relacionados con el
     * comportamiento de los productos Pop en la aplicación.
     */
    protected Logger _logger = LoggerFactory.getLogger(PopProduct.class);

    /**
     * Lista de canciones que se van a crear en la playlist.
     *
     * Este método abstracto debe ser implementado en las clases derivadas para crear una lista
     * de canciones basada en parámetros específicos, como el número de canciones y la API de Spotify.
     *
     * @param spotifyApi La instancia de la API de Spotify utilizada para realizar la búsqueda de canciones.
     * @param amount El número de canciones que se desean obtener.
     * @return Una lista de objetos {@link Song} que representan las canciones que se agregarán a la playlist.
     */
    public abstract List<Song> create(SpotifyApi spotifyApi, int amount);
}
