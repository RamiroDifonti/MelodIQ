package dap.spotifyAPI.products.reggaeton;

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
 * Clase que representa un tipo específico de producto de Reggaeton, destinado a la creación de
 * playlists de reggaeton para fiestas, utilizando un subgénero específico de reggaetón: "Reggaetón Dembow".
 */
public class PartyReggaeton extends ReggaetonProduct {

    /**
     * Crea una lista de canciones de reggaetón para fiesta usando la API de Spotify.
     * Realiza una búsqueda por el subgénero "Reggaetón Dembow" y obtiene las canciones solicitadas.
     *
     * @param spotifyApi La instancia de la API de Spotify utilizada para realizar la búsqueda de canciones.
     * @param amount El número de canciones que se desean obtener.
     * @return Una lista de objetos {@link Song} representando las canciones encontradas.
     */
    public List<Song> create(SpotifyApi spotifyApi, int amount) {
        // Subgénero del reggaetón para fiesta
        String subgenero = "Reggaetón Dembow";

        // Crear la solicitud de búsqueda para obtener las canciones de reggaetón para fiesta
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(subgenero)
                .limit(amount)
                .build();

        List<Song> songs = new ArrayList<>(); // Lista para almacenar las canciones obtenidas

        try {
            // Ejecutar la solicitud de búsqueda y obtener los resultados
            Paging<Track> trackPaging = searchTracksRequest.execute();

            // Procesar los resultados y convertirlos en objetos Song
            Track[] tracks = trackPaging.getItems();

            for (int i = 0; i < tracks.length; i++) {
                // Crear un objeto Song para cada pista obtenida y agregarlo a la lista
                songs.add(new Song(tracks[i]));
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            // En caso de error al obtener las canciones, registrar el error
            _logger.info("Error al obtener las canciones de reggaeton de fiesta: " + e.getMessage());
        }

        return songs; // Devolver la lista de canciones obtenidas
    }
}

