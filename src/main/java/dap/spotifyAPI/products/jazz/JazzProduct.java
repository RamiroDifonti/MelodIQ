package dap.spotifyAPI.products.jazz;

import dap.spotifyAPI.utils.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.michaelthelin.spotify.SpotifyApi;

import java.util.List;

/**
 * Clase abstracta que define el comportamiento de los productos de tipo Jazz.
 *
 * Esta clase proporciona un esquema para las clases concretas que crearán listas de canciones de jazz
 * en función de diferentes criterios, como el estado de ánimo o la actividad.
 */
public abstract class JazzProduct {

  /**
   * Objeto que se encarga de registrar los eventos y errores de la clase {@link JazzProduct}.
   */
  protected Logger _logger = LoggerFactory.getLogger(JazzProduct.class);

  /**
   * Método abstracto que debe ser implementado por las clases concretas para crear una lista de canciones
   * de tipo {@link Song} de jazz.
   *
   * @param spotifyApi Objeto que se encarga de la comunicación con la API de Spotify.
   * @param amount Cantidad de canciones que se van a añadir a la playlist.
   * @return Lista de canciones de tipo {@link Song} correspondientes al tipo de jazz específico.
   */
  public abstract List<Song> create(SpotifyApi spotifyApi, int amount);
}

