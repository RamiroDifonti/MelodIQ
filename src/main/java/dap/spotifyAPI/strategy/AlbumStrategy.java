package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import javax.swing.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class AlbumStrategy implements Strategy {
    private SpotifyInterface spotify;

    public AlbumStrategy(SpotifyInterface spotify) {
        this.spotify = spotify;
    }

    @Override
    public void execute(String artistName) {
        List<AlbumSimplified> albums = spotify.getAlbumsByArtist(artistName);
        if (albums == null || albums.isEmpty()) {
            System.out.println("No se encontraron álbumes para el artista: " + artistName);
            return;
        }

        List<AlbumSimplified> selectedAlbums = selectAlbums(albums);
        List<TrackSimplified> playlistTracks = mergeTracks(selectedAlbums);

        if (!playlistTracks.isEmpty()) {
            savePlaylist(playlistTracks);
        }
    }

    private List<AlbumSimplified> selectAlbums(List<AlbumSimplified> albums) {
        // Interfaz gráfica para seleccionar álbumes
        return albums; // Retorna todos por ahora
    }

    private List<TrackSimplified> mergeTracks(List<AlbumSimplified> albums) {
        List<TrackSimplified> allTracks = new ArrayList<>();
        for (AlbumSimplified album : albums) {
            List<TrackSimplified> tracks = spotify.getTracksByArtist(album.getId());
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
