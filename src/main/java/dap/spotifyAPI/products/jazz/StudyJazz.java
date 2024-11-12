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

public class StudyJazz extends JazzProduct {
  public List<Song> create(SpotifyApi spotifyApi, int amount) {
    String genero = "jazz";
    List<Song> songs = new ArrayList<>();
    SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks("genre:\"jazz\" mood:study")
            .limit(amount)
            .build();
    try {
      // Realizar la solicitud y obtener los resultados
      Paging<Track> trackPaging = searchTracksRequest.execute();

      // Procesar y mostrar los resultados
      Track[] tracks = trackPaging.getItems();

      for (Track track : tracks) {
        songs.add(new Song(track)); // Añadir la canción a la lista
      }
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      _logger.info("Error al obtener las canciones de jazz de estudiar: " + e.getMessage());
    }
    return songs;
  }
}