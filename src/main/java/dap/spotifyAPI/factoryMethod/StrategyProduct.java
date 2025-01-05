package dap.spotifyAPI.factoryMethod;


import dap.spotifyAPI.mvc.MainController;

import javax.swing.*;
import java.awt.*;

public class StrategyProduct extends JPanel implements PatternProduct {
    private final MainController controller;

    public StrategyProduct(MainController controller) {
        this.controller = controller;
    }

    @Override
    public Component display() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Seleccione una Estrategia para Crear su Playlist");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton albumButton = new JButton("Álbumes");
        JButton songButton = new JButton("Canciones");
        JButton playlistButton = new JButton("Playlists");

        albumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        songButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        albumButton.addActionListener(e -> {
            String artistName = JOptionPane.showInputDialog(this, "Introduce el nombre del artista:");
            if (artistName != null && !artistName.trim().isEmpty()) {
                controller.handleStrategy("Album", artistName);
            }
        });

        songButton.addActionListener(e -> {
            String artistName = JOptionPane.showInputDialog(this, "Introduce el nombre del artista:");
            if (artistName != null && !artistName.trim().isEmpty()) {
                controller.handleStrategy("Song", artistName);
            }
        });

        playlistButton.addActionListener(e -> {
            String userId = JOptionPane.showInputDialog(this, "Introduce el nombre del usuario:");
            if (userId != null && !userId.trim().isEmpty()) {
                controller.handleStrategy("Playlist", userId);
            }
        });

        add(Box.createVerticalGlue());
        add(title);
        add(Box.createVerticalStrut(20));
        add(albumButton);
        add(Box.createVerticalStrut(10));
        add(songButton);
        add(Box.createVerticalStrut(10));
        add(playlistButton);
        add(Box.createVerticalGlue());
        return this;
    }
}
