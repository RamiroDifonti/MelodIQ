package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.template.Album;
import dap.spotifyAPI.template.Playlist;
import dap.spotifyAPI.template.SearchTemplate;
import dap.spotifyAPI.template.Track;

import javax.swing.*;
import java.awt.*;

public class TemplateProduct implements PatternProduct {
    @Override
    public JPanel display(SpotifyInterface manager) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel chooseLabel = new JLabel("¿Que desea buscar?");
        chooseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JRadioButton playlistButton = new JRadioButton("Playlist");
        JRadioButton songButton = new JRadioButton("Canción");
        JRadioButton albumButton = new JRadioButton("Álbum");

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
        radioPanel.setMaximumSize(new Dimension(600, 20));
        radioPanel.setLayout(new GridLayout(1, 3));
        radioPanel.add(Box.createHorizontalGlue());
        radioPanel.add(playlistButton);
        radioPanel.add(Box.createHorizontalStrut(10));
        radioPanel.add(songButton);
        radioPanel.add(Box.createHorizontalStrut(10));
        radioPanel.add(albumButton);
        radioPanel.add(Box.createHorizontalGlue());

        panel.add(Box.createVerticalGlue());
        panel.add(chooseLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(radioPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(userLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(userField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(songLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(songField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(search);
        panel.add(Box.createVerticalGlue());
        panel.revalidate();
        panel.repaint();

        search.addActionListener(e -> {
            String user = userField.getText();
            String song = songField.getText();
            if (user.isEmpty() || song.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                SearchTemplate template;
                if (playlistButton.isSelected()) {
                    template = new Playlist();
                } else if (songButton.isSelected()) {
                    template = new Track();
                } else if (albumButton.isSelected()) {
                    template = new Album();
                } else {
                    JOptionPane.showMessageDialog(panel, "Por favor, seleccione una opción de búsqueda", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JScrollPane scrollPane = template.Search(manager, user, song);
                panel.removeAll();
                panel.add(scrollPane);
                JButton backButton = new JButton("Volver");
                backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(backButton);
                backButton.addActionListener(e1 -> {
                    display(manager);
                });
                panel.revalidate();
                panel.repaint();
            }
        });
        return panel;
    }
}
