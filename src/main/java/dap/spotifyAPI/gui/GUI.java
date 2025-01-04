package dap.spotifyAPI.gui;

import dap.spotifyAPI.factoryMethod.*;
import dap.spotifyAPI.mvc.MainController;
import dap.spotifyAPI.proxy.Proxy;
import dap.spotifyAPI.proxy.Spotify;
import dap.spotifyAPI.proxy.SpotifyInterface;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private final MainController controller;

    public GUI() {
        String client_id = "c9072e3391954da9b7caf8cdcb0e8d34";
        String client_secret = "acbab08c63964194bf8989dc832b2947";

        SpotifyInterface spotify = new Proxy(new Spotify(client_id, client_secret));
        controller = new MainController(spotify);

        setTitle("MelodIQ");
        setSize(1920, 1080);
        setLayout(new GridLayout(2, 2));
        setBackground(new Color(20, 140, 90));

        add(observerPanel());
        add(templatePanel());
        add(strategyPanel());

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel observerPanel() {
        JPanel panel = new JPanel();
        JLabel title = new JLabel("Patrón Observer");
        JButton notifyButton = new JButton("Notificar Álbumes");

        notifyButton.addActionListener(e -> {
            String artistName = JOptionPane.showInputDialog(panel, "Introduce el nombre del artista:");
            if (artistName != null && !artistName.trim().isEmpty()) {
                controller.handleObserverNotification(artistName);
            }
        });

        panel.add(title);
        panel.add(notifyButton);
        return panel;
    }

    private JPanel templatePanel() {
        JPanel panel = new JPanel();
        JLabel title = new JLabel("Patrón Template");
        JButton searchAlbumButton = new JButton("Buscar Álbum");
        JButton searchPlaylistButton = new JButton("Buscar Playlist");
        JButton searchTrackButton = new JButton("Buscar Canción");

        searchAlbumButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(panel, "Introduce el nombre del artista:");
            String searchField = JOptionPane.showInputDialog(panel, "Introduce el nombre del álbum:");
            if (name != null && searchField != null) {
                JPanel result = controller.handleSearchTemplate("Album", name, searchField);
                if (result != null) {
                    JFrame resultFrame = new JFrame("Resultados de Búsqueda");
                    resultFrame.add(result);
                    resultFrame.setSize(400, 600);
                    resultFrame.setVisible(true);
                }
            }
        });

        panel.add(title);
        panel.add(searchAlbumButton);
        panel.add(searchPlaylistButton);
        panel.add(searchTrackButton);
        return panel;
    }

    private JPanel strategyPanel() {
        JPanel panel = new JPanel();
        JLabel title = new JLabel("Patrón Strategy");
        JButton strategyButton = new JButton("Ejecutar Estrategia");

        strategyButton.addActionListener(e -> {
            String strategyType = JOptionPane.showInputDialog(panel, "Introduce el tipo de estrategia:");
            String parameter = JOptionPane.showInputDialog(panel, "Introduce el parámetro:");
            if (strategyType != null && parameter != null) {
                controller.handleStrategy(strategyType, parameter);
            }
        });

        panel.add(title);
        panel.add(strategyButton);
        return panel;
    }
}
