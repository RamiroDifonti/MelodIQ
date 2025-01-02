package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;

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
        List<Song> songs = spotify.getTracksByArtist(artistName);
        if (songs == null || songs.isEmpty()) {
            System.out.println("No se encontraron canciones para el artista: " + artistName);
            return;
        }

        List<Song> selectedSongs = selectSongs(songs);

        if (!selectedSongs.isEmpty()) {
            savePlaylist(selectedSongs);
        }
    }

    private List<Song> selectSongs(List<Song> songs) {
        JFrame frame = new JFrame("Seleccionar Canciones");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        List<JCheckBox> checkboxes = new ArrayList<>();
        for (Song song : songs) {
            JCheckBox checkbox = new JCheckBox(song.name);
            checkbox.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(checkbox);
            checkboxes.add(checkbox);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);

        JButton confirmButton = new JButton("Confirmar Selección");
        frame.add(confirmButton, BorderLayout.SOUTH);

        List<Song> selectedSongs = new ArrayList<>();
        confirmButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.size(); i++) {
                if (checkboxes.get(i).isSelected()) {
                    selectedSongs.add(songs.get(i));
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

        return selectedSongs;
    }

    private void savePlaylist(List<Song> songs) {
        try (FileWriter writer = new FileWriter("playlist.txt")) {
            for (Song song : songs) {
                writer.write(song.name + "\n");
            }
            System.out.println("Playlist guardada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
