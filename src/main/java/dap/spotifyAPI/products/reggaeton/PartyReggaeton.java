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

public class PartyReggaeton extends ReggaetonProduct {
    public List<Song> create(SpotifyApi spotifyApi, int amount) {
        // Subgenero del reggaeton para fiesta
        String subgenero = "Reggaetón Dembow";
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
            _logger.info("Error al obtener las canciones de reggaeton de fiesta: " + e.getMessage());
        }
        return songs;
    }
}
