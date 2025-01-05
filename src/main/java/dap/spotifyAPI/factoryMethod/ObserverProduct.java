package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.observer.Artist;
import dap.spotifyAPI.observer.User;
import dap.spotifyAPI.proxy.SpotifyInterface;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ObserverProduct extends JPanel implements PatternProduct {
    private final SpotifyInterface spotify;
    private final Map<String, Artist> artists;
    private final DefaultListModel<String> userListModel;
    private final JTextArea notificationArea;

    public ObserverProduct(SpotifyInterface spotify) {
        this.spotify = spotify;
        this.artists = new HashMap<>();
        this.userListModel = new DefaultListModel<>();
        this.notificationArea = new JTextArea(10, 30);
    }

    @Override
    public Component display() {
        setLayout(new BorderLayout());

        // Panel izquierdo para gestionar usuarios
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        JLabel userLabel = new JLabel("Usuarios:");
        JList<String> userList = new JList<>(userListModel);
        JButton addUserButton = new JButton("Agregar Usuario");

        addUserButton.addActionListener(e -> {
            String userName = JOptionPane.showInputDialog(this, "Introduce el nombre del usuario:");
            if (userName != null && !userName.trim().isEmpty()) {
                userListModel.addElement(userName);
                notificationArea.append("Usuario agregado: " + userName + "\n");
            }
        });

        userPanel.add(userLabel);
        userPanel.add(new JScrollPane(userList));
        userPanel.add(addUserButton);

        // Panel derecho para gestionar artistas y notificaciones
        JPanel artistPanel = new JPanel();
        artistPanel.setLayout(new BoxLayout(artistPanel, BoxLayout.Y_AXIS));
        JLabel artistLabel = new JLabel("Artistas:");
        JTextField artistField = new JTextField();
        JButton subscribeButton = new JButton("Suscribirse");
        JButton notifyButton = new JButton("Notificar Nuevos Álbumes");
        JButton addAlbumButton = new JButton("Agregar Álbum por ID");

        subscribeButton.addActionListener(e -> {
            String artistName = artistField.getText().trim();
            String userName = userList.getSelectedValue();

            if (artistName.isEmpty() || userName == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario y escribe el nombre de un artista.");
                return;
            }

            artists.putIfAbsent(artistName, new Artist(spotify, artistName));
            Artist artist = artists.get(artistName);

            User user = new User(userName);
            artist.addObserver(user);

            notificationArea.append(userName + " ahora está suscrito a " + artistName + "\n");
        });

        notifyButton.addActionListener(e -> {
            String artistName = artistField.getText().trim();

            if (artistName.isEmpty() || !artists.containsKey(artistName)) {
                JOptionPane.showMessageDialog(this, "El artista no existe o no se ha registrado.");
                return;
            }

            Artist artist = artists.get(artistName);
            artist.fetchAlbums();
            notificationArea.append("Se ha notificado a los suscriptores de " + artistName + "\n");
        });

        addAlbumButton.addActionListener(e -> {
            String artistName = artistField.getText().trim();
            String albumId = JOptionPane.showInputDialog(this, "Introduce el ID del álbum:");

            if (artistName.isEmpty() || albumId == null || albumId.trim().isEmpty() || !artists.containsKey(artistName)) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un nombre de artista válido y un ID de álbum.");
                return;
            }

            Artist artist = artists.get(artistName);
            artist.addAlbum(albumId);

            notificationArea.append("Álbum con ID " + albumId + " agregado" + "\n");
        });

        artistPanel.add(artistLabel);
        artistPanel.add(artistField);
        artistPanel.add(subscribeButton);
        artistPanel.add(notifyButton);
        artistPanel.add(addAlbumButton);

        // Área de notificaciones
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BorderLayout());
        JLabel notificationLabel = new JLabel("Notificaciones:");
        notificationArea.setEditable(false);
        notificationPanel.add(notificationLabel, BorderLayout.NORTH);
        notificationPanel.add(new JScrollPane(notificationArea), BorderLayout.CENTER);

        // Añadir los paneles al producto
        add(userPanel, BorderLayout.WEST);
        add(artistPanel, BorderLayout.CENTER);
        add(notificationPanel, BorderLayout.SOUTH);
        return this;
    }
}
