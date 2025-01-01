package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import javax.swing.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class SongStrategy implements Strategy {
    private SpotifyInterface spotify;

    public SongStrategy(SpotifyInterface spotify) {
        this.spotify = spotify;
    }

    @Override
    public void execute(String artistName) {
        List<TrackSimplified> tracks = spotify.getTracksByArtist(artistName);
        if (tracks == null || tracks.isEmpty()) {
            System.out.println("No se encontraron canciones para el artista: " + artistName);
            return;
        }

        List<TrackSimplified> selectedTracks = selectTracks(tracks);

        if (!selectedTracks.isEmpty()) {
            savePlaylist(selectedTracks);
        }
    }

    private List<TrackSimplified> selectTracks(List<TrackSimplified> tracks) {
        // Interfaz gráfica para seleccionar canciones
        return tracks; // Retorna todos por ahora
    }

    private void savePlaylist(List<TrackSimplified> tracks) {
        try (FileWriter writer = new FileWriter("playlist.txt")) {
            for (TrackSimplified track : tracks) {
                writer.write(track.getName() + "\n");
            }
            System.out.println("Playlist guardada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
