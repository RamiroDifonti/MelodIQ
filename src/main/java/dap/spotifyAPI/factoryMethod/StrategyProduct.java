package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.strategy.ContextStrategy;
import dap.spotifyAPI.strategy.AlbumStrategy;
import dap.spotifyAPI.strategy.SongStrategy;
import dap.spotifyAPI.strategy.PlaylistStrategy;

import javax.swing.*;
import java.awt.*;

public class StrategyProduct implements PatternProduct {
    @Override
    public JPanel display(SpotifyInterface manager) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(20, 140, 90));

        JLabel titleLabel = new JLabel("Seleccione una estrategia para crear su Playlist");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton albumButton = new JButton("Álbumes");
        JButton songButton = new JButton("Canciones");
        JButton playlistButton = new JButton("Playlists");

        albumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        songButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(albumButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(songButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(playlistButton);
        panel.add(Box.createVerticalGlue());

        albumButton.addActionListener(e -> {
            ContextStrategy context = new ContextStrategy();
            context.setStrategy(new AlbumStrategy(manager));
            String artistName = JOptionPane.showInputDialog(panel, "Introduce el nombre del artista:", "Álbumes", JOptionPane.QUESTION_MESSAGE);
            if (artistName != null && !artistName.trim().isEmpty()) {
                context.executeStrategy(artistName);
            }
        });

        songButton.addActionListener(e -> {
            ContextStrategy context = new ContextStrategy();
            context.setStrategy(new SongStrategy(manager));
            String artistName = JOptionPane.showInputDialog(panel, "Introduce el nombre del artista:", "Canciones", JOptionPane.QUESTION_MESSAGE);
            if (artistName != null && !artistName.trim().isEmpty()) {
                context.executeStrategy(artistName);
            }
        });

        playlistButton.addActionListener(e -> {
            ContextStrategy context = new ContextStrategy();
            context.setStrategy(new PlaylistStrategy(manager));
            String userId = JOptionPane.showInputDialog(panel, "Introduce el nombre del usuario:", "Playlists", JOptionPane.QUESTION_MESSAGE);
            if (userId != null && !userId.trim().isEmpty()) {
                context.executeStrategy(userId);
            }
        });

        panel.revalidate();
        panel.repaint();
        return panel;
    }
}
