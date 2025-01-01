package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import javax.swing.*;
import java.awt.*;
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

        System.out.println("Canciones encontradas:" + tracks.size());
        for (TrackSimplified track : tracks) {
            System.out.println(track.getName());
        }

        List<TrackSimplified> selectedTracks = selectTracks(tracks);

        if (!selectedTracks.isEmpty()) {
            savePlaylist(selectedTracks);
        }
    }

    private List<TrackSimplified> selectTracks(List<TrackSimplified> tracks) {
        JFrame frame = new JFrame("Seleccionar Canciones");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        List<JCheckBox> checkboxes = new ArrayList<>();
        for (TrackSimplified track : tracks) {
            JCheckBox checkbox = new JCheckBox(track.getName());
            checkbox.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(checkbox);
            checkboxes.add(checkbox);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirmar Selección");
        frame.add(confirmButton, BorderLayout.SOUTH);

        List<TrackSimplified> selectedTracks = new ArrayList<>();
        confirmButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.size(); i++) {
                if (checkboxes.get(i).isSelected()) {
                    selectedTracks.add(tracks.get(i));
                }
            }
            frame.dispose();
        });

        frame.setVisible(true);

//        while (frame.isDisplayable()) {
//            try {
//                Thread.sleep(100); // Espera hasta que se cierre la ventana
//            } catch (InterruptedException ignored) {
//            }
//        }

        return selectedTracks;
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
