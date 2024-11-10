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
 * Clase concreta que define el comportamiento de los productos de tipo Pop para deporte en la aplicación.
 * Esta clase extiende de la clase PopProduct y sobreescribe el metodo create para crear una lista de canciones
 * de pop para deporte.
 */
public class SportPop extends PopProduct {
    /**
     * Metodo que crea una lista de canciones con las características de Pop.
     * @param spotifyApi Objeto que se encarga de la comunicación con la API de Spotify.
     * @param amount Cantidad de canciones que se van a añadir a la playlist.
     * @return Lista de canciones de Pop.
     */
    @Override
    public List<Song> create(SpotifyApi spotifyApi, int amount) {
        // Lista de canciones que se van a añadir a la playlist.
        List<Song> songs = new ArrayList<>();
        String genre = "pop";
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks("genre:\"" + genre + "\"")
                .limit(amount)
                .build();
        try {
            // Solicitamos los tracks a la API de Spotify y los añadimos a la lista de canciones.
            Paging<Track> trackPaging = searchTracksRequest.execute();

            // Iteramos sobre los tracks obtenidos y los añadimos a la lista de canciones.
            for (Track track : trackPaging.getItems()) {
                songs.add(new Song(track.getName(), track.getArtists()[0].getName()));
                System.out.println("Nombre: " + track.getName() + "\nArtista: " + track.getArtists()[0].getName() + "\nURI: " + track.getUri());
            }

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            _logger.info("Error al obtener las canciones de Pop para la playlist de deporte: ", e.getMessage());
        }
        return songs;
    }
}
