package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SongStrategy implements Strategy {
    private SpotifyInterface spotify;

    public SongStrategy(SpotifyInterface spotify) {
        this.spotify = spotify;
    }

    @Override
    public void execute(String artistName) {
        System.out.println("Buscando canciones para el artista: " + artistName);
        List<Song> songs = spotify.getTracksByArtist(artistName);
        if (songs == null || songs.isEmpty()) {
            System.out.println("No se encontraron canciones para el artista: " + artistName);
            return;
        }

        System.out.println("Canciones encontradas:");
        for (Song song : songs) {
            System.out.println(song.name);
        }

        List<Song> selectedSongs = selectSongs(songs);
        if (!selectedSongs.isEmpty()) {
            showSelectedSongs(selectedSongs);
        } else {
            System.out.println("No se seleccionaron canciones.");
        }
    }

    private List<Song> selectSongs(List<Song> songs) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Seleccionar Canciones");
        dialog.setSize(400, 500);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirmar Selección");
        dialog.add(confirmButton, BorderLayout.SOUTH);

        List<Song> selectedSongs = new ArrayList<>();
        confirmButton.addActionListener(e -> {
            for (int i = 0; i < checkboxes.size(); i++) {
                if (checkboxes.get(i).isSelected()) {
                    selectedSongs.add(songs.get(i));
                }
            }
            dialog.dispose();
        });

        dialog.setVisible(true);

        return selectedSongs;
    }

    private void showSelectedSongs(List<Song> selectedSongs) {
        JFrame frame = new JFrame("Canciones Seleccionadas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Song song : selectedSongs) {
            panel.add(song.getLayout());
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
