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

public class SportPop extends PopProduct {
    /**
     * Metodo que crea una lista de canciones con las características de Pop.
     * @param spotifyApi Objeto que se encarga de la comunicación con la API de Spotify.
     * @param amount Cantidad de canciones que se van a añadir a la playlist.
     * @return Lista de canciones de Pop.
     */
    @Override
    public List<Song> create(SpotifyApi spotifyApi, int amount) {
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
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            _logger.info("Error al obtener las canciones de Pop para la playlist de deporte.{}", e.getMessage());
        }
        return songs;
    }
}
