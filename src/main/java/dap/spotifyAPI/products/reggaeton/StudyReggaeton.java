package dap.spotifyAPI.products.reggaeton;

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
 * Clase que representa un producto de reggaetón para estudiar.
 *
 * Esta clase extiende {@link ReggaetonProduct} y implementa el método {@link #create(SpotifyApi, int)}
 * para obtener una lista de canciones de reggaetón con un subgénero específico, en este caso,
 * canciones de "Reggaetón R&B" para crear un ambiente adecuado para estudiar.
 */
public class StudyReggaeton extends ReggaetonProduct {

    /**
     * Crea una lista de canciones de reggaetón con el subgénero "Reggaetón R&B" para estudiar.
     *
     * Este método realiza una búsqueda en la API de Spotify con el subgénero "Reggaetón R&B" y
     * devuelve una lista de canciones obtenidas según la cantidad especificada.
     *
     * @param spotifyApi La instancia de la API de Spotify utilizada para realizar la búsqueda de canciones.
     * @param amount El número de canciones que se desean obtener.
     * @return Una lista de objetos {@link Song} que representan las canciones obtenidas.
     */
    public List<Song> create(SpotifyApi spotifyApi, int amount) {
        // Subgénero del reggaetón para estudiar
        String subgenero = "Reggaetón R&B";
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(subgenero)
                .limit(amount)
                .build();
        List<Song> songs = new ArrayList<>();
        try {
            // Realizar la solicitud y obtener los resultados
            Paging<Track> trackPaging = searchTracksRequest.execute();

            // Procesar y crear las canciones
            Track[] tracks = trackPaging.getItems();

            for (int i = 0; i < tracks.length; i++) {
                songs.add(new Song(tracks[i]));
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            _logger.info("Error al obtener las canciones de reggaeton de estudiar: " + e.getMessage());
        }
        return songs;
    }
}
