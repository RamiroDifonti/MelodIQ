package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import javax.swing.*;
import java.awt.*;
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
        JFrame frame = new JFrame("Seleccionar Playlists");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        List<JCheckBox> checkboxes = new ArrayList<>();
        for (PlaylistSimplified playlist : playlists) {
            JCheckBox checkbox = new JCheckBox(playlist.getName());
            checkbox.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(checkbox);
            checkboxes.add(checkbox);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirmar Selección");
        frame.add(confirmButton, BorderLayout.SOUTH);

        List<PlaylistSimplified> selectedPlaylists = new ArrayList<>();
        confirmButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.size(); i++) {
                if (checkboxes.get(i).isSelected()) {
                    selectedPlaylists.add(playlists.get(i));
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

        return selectedPlaylists;
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
