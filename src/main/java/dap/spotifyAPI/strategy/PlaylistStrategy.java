package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import javax.swing.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class PlaylistStrategy implements Strategy {
    private SpotifyInterface spotify;

    public PlaylistStrategy(SpotifyInterface spotify) {
        this.spotify = spotify;
    }

    @Override
    public void execute(String userId) {
        List<PlaylistSimplified> playlists = spotify.getPlaylistsByUser(userId);
        if (playlists == null || playlists.isEmpty()) {
            System.out.println("No se encontraron playlists para el usuario: " + userId);
            return;
        }

        List<PlaylistSimplified> selectedPlaylists = selectPlaylists(playlists);
        List<TrackSimplified> playlistTracks = mergeTracks(selectedPlaylists);

        if (!playlistTracks.isEmpty()) {
            savePlaylist(playlistTracks);
        }
    }

    private List<PlaylistSimplified> selectPlaylists(List<PlaylistSimplified> playlists) {
        // Interfaz gráfica para seleccionar playlists
        return playlists; // Retorna todos por ahora
    }

    private List<TrackSimplified> mergeTracks(List<PlaylistSimplified> playlists) {
        List<TrackSimplified> allTracks = new ArrayList<>();
        for (PlaylistSimplified playlist : playlists) {
            List<TrackSimplified> tracks = spotify.getTracksByArtist(playlist.getId());
            if (tracks != null) {
                allTracks.addAll(tracks);
            }
        }
        return allTracks;
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
