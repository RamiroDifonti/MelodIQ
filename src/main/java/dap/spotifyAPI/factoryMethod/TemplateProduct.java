package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;

import javax.swing.*;
import java.awt.*;

public class TemplateProduct extends JPanel {
    private final MainController controller;

    public TemplateProduct(MainController controller) {
        this.controller = controller;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Búsquedas");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton searchAlbumButton = new JButton("Buscar Álbumes");
        JButton searchPlaylistButton = new JButton("Buscar Playlists");
        JButton searchTrackButton = new JButton("Buscar Canciones");

        searchAlbumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPlaylistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchTrackButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchAlbumButton.addActionListener(e -> {
            String artistName = JOptionPane.showInputDialog(this, "Introduce el nombre del artista:");
            String albumName = JOptionPane.showInputDialog(this, "Introduce el nombre del álbum:");
            if (artistName != null && albumName != null) {
                controller.handleSearchTemplate("Album", artistName, albumName);
            }
        });

        searchPlaylistButton.addActionListener(e -> {
            String userId = JOptionPane.showInputDialog(this, "Introduce el nombre del usuario:");
            if (userId != null) {
                controller.handleSearchTemplate("Playlist", userId, "");
            }
        });

        searchTrackButton.addActionListener(e -> {
            String artistName = JOptionPane.showInputDialog(this, "Introduce el nombre del artista:");
            String trackName = JOptionPane.showInputDialog(this, "Introduce el nombre de la canción:");
            if (artistName != null && trackName != null) {
                controller.handleSearchTemplate("Track", artistName, trackName);
            }
        });

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
}
