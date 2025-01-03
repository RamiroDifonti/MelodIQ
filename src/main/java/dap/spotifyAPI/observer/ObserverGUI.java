package dap.spotifyAPI.observer;

import dap.spotifyAPI.proxy.Proxy;
import dap.spotifyAPI.proxy.Spotify;
import dap.spotifyAPI.proxy.SpotifyInterface;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ObserverGUI {
    private SpotifyInterface spotify;
    private Artist artist;
    private JTextArea notificationArea;

    public ObserverGUI(SpotifyInterface spotify) {
        this.spotify = spotify;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Observer Pattern - Notificaciones de Álbumes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        JTextField artistField = new JTextField();
        JButton addArtistButton = new JButton("Agregar Artista");

        inputPanel.add(new JLabel("Nombre del Artista:"));
        inputPanel.add(artistField);

        notificationArea = new JTextArea();
        notificationArea.setEditable(false);

        JButton addAlbumButton = new JButton("Agregar Álbum");
        addAlbumButton.setEnabled(false); // Se habilita cuando un artista ha sido seleccionado

        addArtistButton.addActionListener(e -> {
            String artistName = artistField.getText().trim();
            if (artistName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese un nombre de artista.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear el sujeto (artista) y observador (usuario)
            artist = new Artist(spotify, artistName);
            User user = new User("Usuario");
            artist.addObserver(user);

            addAlbumButton.setEnabled(true);

            // Configurar un temporizador para llamadas periódicas a la API
            Timer timer = new Timer(true);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    artist.fetchAlbums();
                    updateNotificationArea();
                }
            }, 0, 2 * 60 * 1000); // Llamadas cada 2 minutos
        });

        addAlbumButton.addActionListener(e -> {
            if (artist == null) return;

            String albumId = JOptionPane.showInputDialog(frame, "Ingrese el ID del álbum:", "Agregar Álbum", JOptionPane.PLAIN_MESSAGE);
            if (albumId != null && !albumId.trim().isEmpty()) {
                artist.addAlbum(albumId);
                updateNotificationArea();
            }
        });

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(notificationArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addArtistButton);
        buttonPanel.add(addAlbumButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void updateNotificationArea() {
        SwingUtilities.invokeLater(() -> {
            if (artist == null) return;

            StringBuilder notifications = new StringBuilder("Notificaciones:\n");
            List<AlbumSimplified> latestAlbums = artist.getLatestAlbums(3);
            for (AlbumSimplified album : latestAlbums) {
                notifications.append("Álbum: ").append(album.getName()).append("\n");
            }
            notificationArea.setText(notifications.toString());
        });
    }

    public static void main(String[] args) {
        SpotifyInterface proxy = new Proxy(new Spotify("c9072e3391954da9b7caf8cdcb0e8d34", "acbab08c63964194bf8989dc832b2947"));
        new ObserverGUI(proxy);
    }
}