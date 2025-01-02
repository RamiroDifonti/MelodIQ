package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

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
        List<Song> playlistTracks = mergeTracks(selectedPlaylists);

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

//        while (frame.isDisplayable()) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ignored) {
//            }
//        }

        return selectedPlaylists;
    }

    private List<Song> mergeTracks(List<PlaylistSimplified> playlists) {
        List<Song> allTracks = new ArrayList<>();
        for (PlaylistSimplified playlist : playlists) {
            List<Song> tracks = spotify.getPlaylistTracks(playlist.getId());
            if (tracks != null) {
                allTracks.addAll(tracks);
            }
        }
        return allTracks;
    }

    private void savePlaylist(List<Song> tracks) {
        try (FileWriter writer = new FileWriter("playlist.txt")) {
            for (Song track : tracks) {
                writer.write(track.name + "\n");
            }
            System.out.println("Playlist guardada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
