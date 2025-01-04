package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.template.Album;
import dap.spotifyAPI.template.Playlist;
import dap.spotifyAPI.template.Track;
import dap.spotifyAPI.proxy.SpotifyInterface;

import javax.swing.*;
import java.awt.*;

public class TemplateProduct extends JPanel {
    private final SpotifyInterface spotify;

    public TemplateProduct(SpotifyInterface spotify) {
        this.spotify = spotify;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Búsquedas");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botones de búsqueda
        JButton searchAlbumButton = new JButton("Buscar Álbumes");
        JButton searchPlaylistButton = new JButton("Buscar Playlists");
        JButton searchTrackButton = new JButton("Buscar Canciones");

        searchAlbumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPlaylistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchTrackButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Acción para buscar álbumes
        searchAlbumButton.addActionListener(e -> {
            String artistName = JOptionPane.showInputDialog(this, "Introduce el nombre del artista:");
            String albumName = JOptionPane.showInputDialog(this, "Introduce el nombre del álbum:");
            if (artistName != null && albumName != null && !artistName.trim().isEmpty() && !albumName.trim().isEmpty()) {
                JPanel resultsPanel = new Album().Search(spotify, artistName, albumName);
                showResults(resultsPanel, "Resultados de Álbumes");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, introduce valores válidos.");
            }
        });

        // Acción para buscar playlists
        searchPlaylistButton.addActionListener(e -> {
            String userId = JOptionPane.showInputDialog(this, "Introduce el nombre del usuario:");
            if (userId != null && !userId.trim().isEmpty()) {
                JPanel resultsPanel = new Playlist().Search(spotify, userId, "");
                showResults(resultsPanel, "Resultados de Playlists");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, introduce valores válidos.");
            }
        });

        // Acción para buscar canciones
        searchTrackButton.addActionListener(e -> {
            String artistName = JOptionPane.showInputDialog(this, "Introduce el nombre del artista:");
            String trackName = JOptionPane.showInputDialog(this, "Introduce el nombre de la canción:");
            if (artistName != null && trackName != null && !artistName.trim().isEmpty() && !trackName.trim().isEmpty()) {
                JPanel resultsPanel = new Track().Search(spotify, artistName, trackName);
                showResults(resultsPanel, "Resultados de Canciones");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, introduce valores válidos.");
            }
        });

        // Añadir elementos a la interfaz
        add(Box.createVerticalGlue());
        add(title);
        add(Box.createVerticalStrut(20));
        add(searchAlbumButton);
        add(Box.createVerticalStrut(10));
        add(searchPlaylistButton);
        add(Box.createVerticalStrut(10));
        add(searchTrackButton);
        add(Box.createVerticalGlue());
    }

    /**
     * Muestra los resultados de la búsqueda en un cuadro de diálogo.
     *
     * @param resultsPanel Panel con los resultados de la búsqueda.
     * @param title Título del cuadro de diálogo.
     */
    private void showResults(JPanel resultsPanel, String title) {
        if (resultsPanel != null) {
            JFrame resultsFrame = new JFrame(title);
            resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            resultsFrame.setSize(600, 400);
            resultsFrame.setLayout(new BorderLayout());
            resultsFrame.add(new JScrollPane(resultsPanel), BorderLayout.CENTER);
            resultsFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados.");
        }
    }
}
