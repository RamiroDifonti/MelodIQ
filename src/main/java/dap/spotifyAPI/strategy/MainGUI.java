package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.Proxy;
import dap.spotifyAPI.proxy.Spotify;
import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;

import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private ContextStrategy context;
    private SpotifyInterface spotify;

    public MainGUI(SpotifyInterface spotify) {
        this.spotify = spotify;
        this.context = new ContextStrategy();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Spotify Playlist Creator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton albumButton = new JButton("Álbumes");
        albumButton.addActionListener(e -> {
            context.setStrategy(new AlbumStrategy(spotify));
            // Abrir ventana para ingresar el nombre del artista
            String artistName = JOptionPane.showInputDialog(frame, "Introduce el nombre del artista:");
            if (artistName != null && !artistName.trim().isEmpty()) {
                context.executeStrategy(artistName);
            }
        });

        JButton songButton = new JButton("Canciones");
        songButton.addActionListener(e -> {
            context.setStrategy(new SongStrategy(spotify));
            // Abrir ventana para ingresar el nombre del artista
            String artistName = JOptionPane.showInputDialog(frame, "Introduce el nombre del artista:");
            if (artistName != null && !artistName.trim().isEmpty()) {
                context.executeStrategy(artistName);
            }
        });

        JButton playlistButton = new JButton("Playlists");
        playlistButton.addActionListener(e -> {
            context.setStrategy(new PlaylistStrategy(spotify));
            // Abrir ventana para ingresar el nombre del usuario
            String userId = JOptionPane.showInputDialog(frame, "Introduce el nombre del usuario:");
            if (userId != null && !userId.trim().isEmpty()) {
                context.executeStrategy(userId);
            }
        });

        panel.add(albumButton);
        panel.add(songButton);
        panel.add(playlistButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        String client_id = "75399c2bdb7948b882f6647795204070";
        String client_secret = "484b0ac5151f4288a40c06e8ae7a4dc4";

        // Pasar el Proxy conectado a la API real
        SpotifyInterface proxy = new Proxy(new Spotify(client_id, client_secret));
        new MainGUI(proxy);
    }

//    public static void main(String[] args) {
//        String client_id = "75399c2bdb7948b882f6647795204070";
//        String client_secret = "484b0ac5151f4288a40c06e8ae7a4dc4";
//
//        SpotifyInterface spotify = new Spotify(client_id, client_secret);
//        SpotifyInterface manager = new Proxy(spotify);
//
//        String artistId = "grela2235";
//        System.out.println("Track name: " + manager.getPlaylistsByUser(artistId));
//    }
}
