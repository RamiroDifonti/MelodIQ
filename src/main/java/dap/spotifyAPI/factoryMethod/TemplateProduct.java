package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TemplateProduct extends JPanel implements PatternProduct {
    private final MainController _controller;

    public TemplateProduct(MainController controller) {
        this._controller = controller;
    }

    @Override
    public Component display() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Búsquedas");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(new Color(20, 140, 90));
        Border grayBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        setBorder(grayBorder);

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
                JPanel resultsPanel = _controller.handleSearchTemplate("Album", artistName, albumName);
                showResults(resultsPanel, "Resultados de Álbumes");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, introduce valores válidos.");
            }
        });

        // Acción para buscar playlists
        searchPlaylistButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Introduce el nombre del usuario:");
            String playlistName = JOptionPane.showInputDialog(this, "Introduce el nombre de la playlist:");
            if (username != null && playlistName != null && !username.trim().isEmpty() && !playlistName.trim().isEmpty()) {
                JPanel resultsPanel = _controller.handleSearchTemplate("Playlist", username, playlistName);
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
                JPanel resultsPanel = _controller.handleSearchTemplate("Track", artistName, trackName);
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
        return this;
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
