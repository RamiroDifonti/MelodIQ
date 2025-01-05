package dap.spotifyAPI.facade;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;
import dap.spotifyAPI.utils.YouTubePlayerJavaFX;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoReproducer {
    private final SpotifyInterface _manager;
    private final String _youtubeApiKey;

    public VideoReproducer(SpotifyInterface manager, String youtubeApiKey) {
        this._manager = manager;
        this._youtubeApiKey = youtubeApiKey;
    }
    public void SongReproducer(String artistName) {
        // Step 1: Request the song from an artist
        SongFinder(artistName, callback -> {
            try {
                // Step 2: Search the song on Youtube
                String videoId = SearchYoutubeVideo(callback);
                // Step 3: Play the song
                PlayVideo(videoId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void SongFinder(String artistName, Consumer<String> callback) {
        JFrame searchFrame = new JFrame("Selecciona una canción");
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchFrame.setSize(800, 600);
        searchFrame.setLayout(new BorderLayout());

        JLabel chooseLabel = new JLabel("Selecciona una canción:");
        searchFrame.add(chooseLabel, BorderLayout.NORTH);

        List<Song> artistSongs = _manager.getTracksByArtist(artistName);
        if (artistSongs == null || artistSongs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron canciones para el artista " + artistName);
            return;
        }

        JPanel songPanel = new JPanel();
        songPanel.setLayout(new BoxLayout(songPanel, BoxLayout.Y_AXIS));

        for (Song song : artistSongs) {
            JPanel songLayout = new JPanel();
            songLayout.setLayout(new FlowLayout(FlowLayout.LEFT));

            songLayout.add(song.getLayout());
            JButton songButton = new JButton("Reproducir");
            songButton.setBackground(new Color(20, 140, 90));
            songLayout.add(songButton);
            songPanel.add(songLayout);
            songButton.addActionListener(e -> {
                StringBuilder trackName = new StringBuilder();
                for (ArtistSimplified artist : song.getArtists()) {
                    trackName.append(artist.getName()).append(" ");
                }
                trackName.append(" - ");
                trackName.append(song.getName());
                callback.accept(trackName.toString());
                searchFrame.dispose();
            });
        }
        JScrollPane scrollPane = new JScrollPane(songPanel);
        scrollPane.setBackground(new Color(20, 140, 90));
        searchFrame.add(scrollPane, BorderLayout.CENTER);
        searchFrame.setVisible(true);
    }
    private String SearchYoutubeVideo(String trackName) {
        try {
            String encodedSongName = URLEncoder.encode(trackName, "UTF-8");
            String urlString = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + encodedSongName + "&key=" + this._youtubeApiKey;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Utilizar expresiones regulares para obtener el videoId del video
            String regex = "\"videoId\":\\s*\"([A-Za-z0-9_-]+)\"";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(response.toString());
            String videoId = "";
            if (matcher.find()) {
                videoId = matcher.group(1); // Obtener el primer grupo que corresponde al videoId
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void PlayVideo(String videoId) {
        try {
            // Lanzar el reproductor de Youtube
            YouTubePlayerJavaFX.setVideoId(videoId);
            YouTubePlayerJavaFX.launch(YouTubePlayerJavaFX.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
