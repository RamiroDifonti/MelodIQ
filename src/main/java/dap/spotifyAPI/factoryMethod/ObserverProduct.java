package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.observer.Artist;
import dap.spotifyAPI.observer.User;
import dap.spotifyAPI.proxy.SpotifyInterface;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ObserverProduct implements PatternProduct {
    private Artist artist;
    private JTextArea notificationArea;

    @Override
    public JPanel display(SpotifyInterface manager) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240)); // Fondo claro

        // Panel de contenido central
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(40, 40, 20, 40));
        contentPanel.setBackground(new Color(240, 240, 240));

        // Entrada de texto y botones
        JLabel artistLabel = new JLabel("Nombre del Artista:");
        artistLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField artistField = new JTextField();
        artistField.setFont(new Font("Arial", Font.PLAIN, 14));
        artistField.setPreferredSize(new Dimension(200, 30)); // Tamaño más pequeño
        artistField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), // Borde externo
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Espaciado interno
        ));

        JButton addArtistButton = new JButton("Agregar Artista");
        addArtistButton.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton addAlbumButton = new JButton("Agregar Álbum");
        addAlbumButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addAlbumButton.setEnabled(false);

        contentPanel.add(artistLabel);
        contentPanel.add(Box.createVerticalStrut(10)); // Espacio entre componentes
        contentPanel.add(artistField);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(addArtistButton);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(addAlbumButton);

        // Área de notificaciones
        JLabel notificationLabel = new JLabel("Notificaciones:");
        notificationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        notificationArea = new JTextArea(10, 30);
        notificationArea.setFont(new Font("Arial", Font.PLAIN, 12));
        notificationArea.setEditable(false);
        notificationArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        JScrollPane scrollPane = new JScrollPane(notificationArea);

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(notificationLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(scrollPane);

        panel.add(contentPanel, BorderLayout.CENTER);

        // Listeners para los botones
        addArtistButton.addActionListener(e -> {
            String artistName = artistField.getText().trim();
            if (artistName.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Por favor, ingrese un nombre de artista.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear el sujeto (artista) y observador (usuario)
            artist = new Artist(manager, artistName);
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

            String albumId = JOptionPane.showInputDialog(panel, "Ingrese el ID del álbum:", "Agregar Álbum", JOptionPane.PLAIN_MESSAGE);
            if (albumId != null && !albumId.trim().isEmpty()) {
                artist.addAlbum(albumId);
                updateNotificationArea();
            }
        });

        return panel;
    }

    private void updateNotificationArea() {
        SwingUtilities.invokeLater(() -> {
            if (artist == null) return;

            StringBuilder notifications = new StringBuilder();
            List<AlbumSimplified> latestAlbums = artist.getLatestAlbums(3);
            for (AlbumSimplified album : latestAlbums) {
                notifications.append("Álbum: ").append(album.getName()).append("\n");
            }
            notificationArea.setText(notifications.toString());
        });
    }
}


