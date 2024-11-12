package dap.spotifyAPI.products.jazz;

import dap.spotifyAPI.utils.Song;
import org.apache.hc.core5.http.ParseException;
import org.apache.log4j.LogMF;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase concreta que define el comportamiento de los productos de tipo Jazz para fiesta.
 * Esta clase extiende la clase {@link JazzProduct} y sobreescribe el método {@link #create(SpotifyApi, int)}
 * para crear una lista de canciones de jazz adecuadas para una fiesta.
 */
public class PartyJazz extends JazzProduct {

  /**
   * Método que crea una lista de canciones de jazz para fiesta.
   * Realiza una búsqueda en la API de Spotify utilizando el género "jazz" y el estado de ánimo "party".
   *
   * @param spotifyApi Objeto que se encarga de realizar la comunicación con la API de Spotify.
   * @param amount Cantidad de canciones que se van a añadir a la playlist.
   * @return Lista de canciones de jazz para fiesta.
   */
  @Override
  public List<Song> create(SpotifyApi spotifyApi, int amount) {
    String genero = "jazz";
    List<Song> songs = new ArrayList<>();

    // Realizar la solicitud a la API de Spotify para obtener las canciones de jazz para fiesta
    SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks("genre:\"jazz\" mood:party")
            .limit(amount)
            .build();

    try {
      // Realizamos la solicitud y obtenemos los resultados
      Paging<Track> trackPaging = searchTracksRequest.execute();

      // Se procesan los resultados y se añaden las canciones a la lista
      Track[] tracks = trackPaging.getItems();

      for (Track track : tracks) {
        songs.add(new Song(track)); // Añadir la canción a la lista
      }
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      // Si ocurre un error al obtener las canciones, se registra un mensaje en los logs
      _logger.info("Error al obtener las canciones de jazz de fiesta: " + e.getMessage());
    }

    return songs;
  }
}
