package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistStrategy implements Strategy {
    private SpotifyInterface spotify;

    public PlaylistStrategy(SpotifyInterface spotify) {
        this.spotify = spotify;
    }

    @Override
    public void execute(String userId) {
        System.out.println("Buscando playlists para el usuario: " + userId);
        List<PlaylistSimplified> playlists = spotify.getPlaylistsByUser(userId);
        if (playlists == null || playlists.isEmpty()) {
            System.out.println("No se encontraron playlists para el usuario: " + userId);
            return;
        }

        System.out.println("Playlists encontradas:");
        for (PlaylistSimplified playlist : playlists) {
            System.out.println(playlist.getName());
        }

        List<PlaylistSimplified> selectedPlaylists = selectPlaylists(playlists);
        if (!selectedPlaylists.isEmpty()) {
            showPlaylistTracks(selectedPlaylists);
        } else {
            System.out.println("No se seleccionaron playlists.");
        }
    }

    private List<PlaylistSimplified> selectPlaylists(List<PlaylistSimplified> playlists) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Seleccionar Playlists");
        dialog.setSize(400, 500);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirmar Selección");
        dialog.add(confirmButton, BorderLayout.SOUTH);

        List<PlaylistSimplified> selectedPlaylists = new ArrayList<>();
        confirmButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.size(); i++) {
                if (checkboxes.get(i).isSelected()) {
                    selectedPlaylists.add(playlists.get(i));
                }
            }
            dialog.dispose();
        });

        dialog.setVisible(true);

        return selectedPlaylists;
    }

    private void showPlaylistTracks(List<PlaylistSimplified> playlists) {
        System.out.println("Mostrando canciones de las playlists seleccionadas");
        List<Song> allTracks = new ArrayList<>();
        for (PlaylistSimplified playlist : playlists) {
            List<Song> playlistTracks = spotify.getPlaylistTracks(playlist.getId());
            if (playlistTracks != null) {
                allTracks.addAll(playlistTracks);
            }
        }

        JFrame frame = new JFrame("Canciones de las Playlists Seleccionadas");
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
