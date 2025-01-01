package dap.spotifyAPI.strategy;

import dap.spotifyAPI.proxy.SpotifyInterface;

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
            new AlbumGUI(context);
        });

        JButton songButton = new JButton("Canciones");
        songButton.addActionListener(e -> {
            context.setStrategy(new SongStrategy(spotify));
            new SongGUI(context);
        });

        JButton playlistButton = new JButton("Playlists");
        playlistButton.addActionListener(e -> {
            context.setStrategy(new PlaylistStrategy(spotify));
            new PlaylistGUI(context);
        });

        panel.add(albumButton);
        panel.add(songButton);
        panel.add(playlistButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Pasar el Proxy conectado a la API real
        SpotifyInterface proxy = new Proxy(new Spotify("clientId", "clientSecret"));
        new MainGUI(proxy);
    }
}