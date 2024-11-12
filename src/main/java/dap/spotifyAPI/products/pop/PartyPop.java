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

public class PartyPop extends PopProduct {
    public List<Song> create(SpotifyApi spotifyApi, int amount) {
        String genero = "pop";
        List<Song> songs = new ArrayList<>();
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks("genre:\"" + genero + "\" mood:party")
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
