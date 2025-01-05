package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.template.Album;
import dap.spotifyAPI.template.Playlist;
import dap.spotifyAPI.template.SearchTemplate;
import dap.spotifyAPI.template.Track;

import javax.swing.*;
import java.awt.*;

public class TemplateProduct implements PatternProduct {
    private JPanel _songPanel;
    private JPanel _panel;
    @Override
    public JPanel display(SpotifyInterface manager) {
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.Y_AXIS));
        _panel.setBackground(new Color(20, 140, 90));
        JLabel chooseLabel = new JLabel("¿Que desea buscar?");
        chooseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JRadioButton playlistButton = new JRadioButton("Playlist");
        JRadioButton songButton = new JRadioButton("Canción");
        JRadioButton albumButton = new JRadioButton("Álbum");

        playlistButton.setBackground(new Color(20, 140, 90));
        songButton.setBackground(new Color(20, 140, 90));
        albumButton.setBackground(new Color(20, 140, 90));

        // Crear un ButtonGroup para exclusividad mutua
        ButtonGroup group = new ButtonGroup();
        group.add(playlistButton);
        group.add(songButton);
        group.add(albumButton);

        JLabel userLabel = new JLabel("Introduzca el nombre de usuario o artista:");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(200, 20));
        JLabel songLabel = new JLabel("Introduzca el nombre de la canción o albúm:");
        songLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField songField = new JTextField();
        songField.setMaximumSize(new Dimension(200, 20));
        JButton search = new JButton("Buscar");
        search.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel radioPanel = new JPanel();
        radioPanel.setBackground(new Color(20, 140, 90));
        radioPanel.setMaximumSize(new Dimension(600, 20));
        radioPanel.setLayout(new GridLayout(1, 3));
        radioPanel.add(Box.createHorizontalGlue());
        radioPanel.add(playlistButton);
        radioPanel.add(Box.createHorizontalStrut(10));
        radioPanel.add(songButton);
        radioPanel.add(Box.createHorizontalStrut(10));
        radioPanel.add(albumButton);
        radioPanel.add(Box.createHorizontalGlue());

        _panel.add(Box.createVerticalGlue());
        _panel.add(chooseLabel);
        _panel.add(Box.createVerticalStrut(10));
        _panel.add(radioPanel);
        _panel.add(Box.createVerticalStrut(20));
        _panel.add(userLabel);
        _panel.add(Box.createVerticalStrut(10));
        _panel.add(userField);
        _panel.add(Box.createVerticalStrut(20));
        _panel.add(songLabel);
        _panel.add(Box.createVerticalStrut(10));
        _panel.add(songField);
        _panel.add(Box.createVerticalStrut(20));
        _panel.add(search);
        _panel.add(Box.createVerticalGlue());

        search.addActionListener(e -> {
            String user = userField.getText();
            String song = songField.getText();
            if (user.isEmpty() || song.isEmpty()) {
                JOptionPane.showMessageDialog(_panel, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                SearchTemplate template;
                if (playlistButton.isSelected()) {
                    template = new Playlist();
                } else if (songButton.isSelected()) {
                    template = new Track();
                } else if (albumButton.isSelected()) {
                    template = new Album();
                } else {
                    JOptionPane.showMessageDialog(_panel, "Por favor, seleccione una opción de búsqueda", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                _songPanel = template.Search(manager, user, song);
                if (_songPanel == null) {
                    return;
                }
                JFrame searchFrame = new JFrame("Resultados de la búsqueda");
                searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                searchFrame.setSize(800, 600);
                searchFrame.setVisible(true);
                searchFrame.setLayout(new BorderLayout());
                JScrollPane scrollPane = new JScrollPane(_songPanel);
                searchFrame.add(scrollPane, BorderLayout.CENTER);
            }
        });
        return _panel;
    }
}
