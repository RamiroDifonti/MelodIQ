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
 * Esta clase extiende de la clase PopProduct y sobreescribe el metodo create para crear una lista de canciones
 * de pop para estudio.
 */
public class StudyPop extends PopProduct {
    /**
     * Metodo que crea una lista de canciones de pop para estudio.
     * @param spotifyApi Objeto que se encarga de realizar las solicitudes a la API de Spotify.
     * @param amount Cantidad de canciones que se van a crear en la playlist.
     * @return Lista de canciones de pop para estudio.
     */
    public List<Song> create(SpotifyApi spotifyApi, int amount) {
        String genero = "pop";
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks("genre:\"" + genero + "\" mood:study")
                .limit(amount)
                .build();
        List<Song> songs = new ArrayList<>();

        try {
            // Realizamos la solicitud y obtenemos los resultados
            Paging<Track> trackPaging = searchTracksRequest.execute();

            // Se procesan y se muestran los resultados
            Track[] tracks = trackPaging.getItems();

            // Se recorren las canciones obtenidas y se muestran en consola
            for (Track track : tracks) {
                songs.add(new Song(track)); // Añadir la canción a la lista
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            _logger.info("Error al obtener canciones de pop para estudio: " + e.getMessage());
        }
        return songs;
    }
}
