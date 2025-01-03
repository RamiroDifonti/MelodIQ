package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

import javax.swing.*;
import java.awt.*;
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
        if (!selectedAlbums.isEmpty()) {
            showAlbumTracks(selectedAlbums);
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

//        while (frame.isDisplayable()) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ignored) {
//            }
//        }

        return selectedAlbums;
    }

    private void showAlbumTracks(List<AlbumSimplified> albums) {
        List<Song> allTracks = new ArrayList<>();
        for (AlbumSimplified album : albums) {
            List<Song> albumTracks = spotify.getAlbumTracks(album.getId());
            if (albumTracks != null) {
                allTracks.addAll(albumTracks);
            }
        }

        JFrame frame = new JFrame("Canciones de los Álbumes Seleccionados");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Song track : allTracks) {
            panel.add(track.getLayout());
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
