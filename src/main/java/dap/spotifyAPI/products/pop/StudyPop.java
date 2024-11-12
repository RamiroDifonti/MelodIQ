package dap.spotifyAPI.products.pop;

import dap.spotifyAPI.utils.Song;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase concreta que define el comportamiento de los productos de tipo Pop para estudio en la aplicación.
 *
 * Esta clase extiende de {@link PopProduct} y sobreescribe el método {@link #create(SpotifyApi, int)} para
 * crear una lista de canciones de pop relacionadas con el estado de ánimo de estudio.
 */
public class StudyPop extends PopProduct {

    /**
     * Método que crea una lista de canciones de pop para estudiar.
     *
     * Realiza una solicitud a la API de Spotify para obtener canciones de pop relacionadas con el estado de ánimo
     * de estudio y las agrega a una lista de canciones de tipo {@link Song}.
     *
     * @param spotifyApi Objeto que se encarga de la comunicación con la API de Spotify.
     * @param amount La cantidad de canciones que se van a añadir a la playlist.
     * @return Lista de canciones de tipo {@link Song} correspondientes al subgénero de Pop para estudiar.
     */
    public List<Song> create(SpotifyApi spotifyApi, int amount) {
        // Lista de canciones que se van a añadir a la playlist.
        List<Song> songs = new ArrayList<>();

        // Realizamos la solicitud a la API de Spotify para obtener las canciones de Pop para estudiar.
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks("genre:\"pop\" mood:study")
                .limit(amount)
                .build();

        try {
            // Solicitamos los tracks a la API de Spotify y los añadimos a la lista de canciones.
            Paging<Track> trackPaging = searchTracksRequest.execute();

            // Se procesan los tracks obtenidos.
            Track[] tracks = trackPaging.getItems();

            // Se recorren los tracks obtenidos y se añaden a la lista de canciones.
            for (Track track : tracks) {
                songs.add(new Song(track));  // Añadimos la canción a la lista.
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            _logger.info("Error al obtener canciones de pop para estudio: " + e.getMessage());
        }

        return songs;  // Retornamos la lista de canciones obtenidas.
    }
}

