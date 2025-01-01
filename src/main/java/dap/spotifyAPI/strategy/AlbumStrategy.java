package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import javax.swing.*;
import java.awt.*;
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

        System.out.println("Álbumes encontrados:" + albums.size());
        for (AlbumSimplified album : albums) {
            System.out.println(album.getName());
        }


        List<AlbumSimplified> selectedAlbums = selectAlbums(albums);

//        for (AlbumSimplified album : selectedAlbums) {
//            System.out.println(album.getName());
//        }
//
        List<TrackSimplified> playlistTracks = mergeTracks(selectedAlbums);
//
//        for (TrackSimplified track : playlistTracks) {
//            System.out.println(track.getName());
//        }


        if (!playlistTracks.isEmpty()) {
            savePlaylist(playlistTracks);
        }
    }

    private List<AlbumSimplified> selectAlbums(List<AlbumSimplified> albums) {
        JFrame frame = new JFrame("Seleccionar Álbumes");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        List<JCheckBox> checkboxes = new ArrayList<>();
        for (AlbumSimplified album : albums) {
            JCheckBox checkbox = new JCheckBox(album.getName());
            checkbox.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(checkbox);
            checkboxes.add(checkbox);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirmar Selección");
        frame.add(confirmButton, BorderLayout.SOUTH);

        List<AlbumSimplified> selectedAlbums = new ArrayList<>();
        confirmButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.size(); i++) {
                if (checkboxes.get(i).isSelected()) {
                    selectedAlbums.add(albums.get(i));
                }
            }
            frame.dispose();
        });

        frame.setVisible(true);

        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100); // Espera hasta que se cierre la ventana
            } catch (InterruptedException ignored) {
            }
        }

        return selectedAlbums;
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
