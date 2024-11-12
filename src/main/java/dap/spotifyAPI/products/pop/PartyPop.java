package dap.spotifyAPI.products.pop;

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
 * Clase que representa un producto de pop para fiestas.
 *
 * Esta clase extiende {@link PopProduct} y sobrecarga el método {@link #create(SpotifyApi, int)}
 * para obtener una lista de canciones de pop específicamente para fiestas, utilizando los parámetros
 * adecuados en la búsqueda a través de la API de Spotify.
 */
public class PartyPop extends PopProduct {

    /**
     * Crea una lista de canciones de pop para fiestas.
     *
     * Este método realiza una búsqueda en la API de Spotify utilizando el género "pop" y el estado de ánimo "party",
     * y devuelve una lista de canciones obtenidas según la cantidad especificada.
     *
     * @param spotifyApi La instancia de la API de Spotify utilizada para realizar la búsqueda de canciones.
     * @param amount El número de canciones que se desean obtener.
     * @return Una lista de objetos {@link Song} que representan las canciones obtenidas.
     */
    public List<Song> create(SpotifyApi spotifyApi, int amount) {
        List<Song> songs = new ArrayList<>();
        // Realizar la solicitud a la API de Spotify para obtener las canciones de Pop para fiesta
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks("genre:\"pop\" mood:party")
                .limit(amount)
                .build();

        try {
            // Realizar la solicitud y obtener los resultados
            Paging<Track> trackPaging = searchTracksRequest.execute();

            // Procesar y mostrar los resultados
            Track[] tracks = trackPaging.getItems();

            for (Track track : tracks) {
                songs.add(new Song(track)); // Añadimos la canción a la lista de canciones
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            _logger.info("Error al obtener canciones de pop para fiesta: " + e.getMessage());
        }
        return songs;
    }
}

