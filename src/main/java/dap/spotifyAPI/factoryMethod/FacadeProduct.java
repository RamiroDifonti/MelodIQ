package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.facade.VideoReproducer;
import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.template.Album;
import dap.spotifyAPI.template.Playlist;
import dap.spotifyAPI.template.SearchTemplate;
import dap.spotifyAPI.template.Track;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FacadeProduct implements PatternProduct {
    private JPanel _songPanel;
    private JPanel _panel;
    private SpotifyInterface _manager;
    private Component[] _components = new Component[20];
    @Override
    public JPanel display(SpotifyInterface manager) {
        _manager = manager;
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.Y_AXIS));
        _panel.setBackground(new Color(20, 140, 90));
        JLabel artistLabel = new JLabel("Introduzca un artista para reproducir un video");
        artistLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField artistField = new JTextField();
        artistField.setMaximumSize(new Dimension(200, 20));
        JButton search = new JButton("Buscar");
        search.setAlignmentX(Component.CENTER_ALIGNMENT);

        _panel.add(Box.createVerticalGlue());
        _panel.add(artistLabel);
        _panel.add(Box.createVerticalStrut(10));
        _panel.add(artistField);
        _panel.add(Box.createVerticalStrut(20));
        _panel.add(search);
        _panel.add(Box.createVerticalGlue());

        search.addActionListener(e -> {
            String artist = artistField.getText();
            if (artist.isEmpty()) {
                JOptionPane.showMessageDialog(_panel, "Por favor, introduzca un artista", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                VideoReproducer videoReproducer = new VideoReproducer(_manager, "AIzaSyC4TFkCYgOZeXeAUYpfmM2A6XFMqtRXSqI");
                videoReproducer.SongReproducer(artist);
            }
        });
        return _panel;
    }
}
