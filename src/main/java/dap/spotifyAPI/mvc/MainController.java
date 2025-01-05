package dap.spotifyAPI.mvc;

import dap.spotifyAPI.facade.VideoReproducer;
import dap.spotifyAPI.observer.User;
import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.strategy.ContextStrategy;
import dap.spotifyAPI.strategy.AlbumStrategy;
import dap.spotifyAPI.strategy.SongStrategy;
import dap.spotifyAPI.strategy.PlaylistStrategy;
import dap.spotifyAPI.template.Album;
import dap.spotifyAPI.template.Playlist;
import dap.spotifyAPI.template.Track;
import dap.spotifyAPI.observer.Artist;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

import javax.swing.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    private final SpotifyInterface spotify;
    private final Map<String, Artist> artists;

    public MainController(SpotifyInterface spotify) {
        this.spotify = spotify;
        this.artists = new HashMap<>();
    }


    public boolean handleAddArtist(String artistName, String username) {
        if (artistName.isEmpty() || username == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un usuario y escribe el nombre de un artista.");
            return false;
        }

        artists.putIfAbsent(artistName, new Artist(spotify, artistName));

        Artist artist = artists.get(artistName);

        User user = new User(username);
        artist.addObserver(user);
        return true;
    }
    /**
     * Maneja eventos relacionados con el patrón Observer
     */
    public String handleObserverNotification(String artistName) {
        if (artistName == null || artistName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del artista no puede estar vacío.");
            return null;
        }

        // Registrar el artista si no existe
        artists.putIfAbsent(artistName, new Artist(spotify, artistName));
        Artist artist = artists.get(artistName);

        List<AlbumSimplified> latestAlbums = artist.getLatestAlbums(3);
        if (latestAlbums.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron álbumes recientes para " + artistName);
            return null;
        } else {
            StringBuilder message = new StringBuilder("Últimos álbumes de " + artistName + ":\n");
            for (AlbumSimplified album : latestAlbums) {
                message.append("- ").append(album.getName()).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString());
            return message.toString();
        }
    }

    /**
     * Agrega un álbum por su ID al artista y notifica a los observadores.
     */
    public boolean handleAddAlbum(String artistName, String albumId) {
        if (artistName == null || artistName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del artista no puede estar vacío.");
            return false;
        }
        if (albumId == null || albumId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El ID del álbum no puede estar vacío.");
            return false;
        }

        // Registrar el artista si no existe
        artists.putIfAbsent(artistName, new Artist(spotify, artistName));
        Artist artist = artists.get(artistName);

        // Agregar el álbum y notificar
        artist.addAlbum(albumId);
        JOptionPane.showMessageDialog(null, "Álbum agregado y suscriptores notificados para " + artistName);
        return true;
    }

    /**
     * Maneja eventos relacionados con el patrón Strategy
     */
    public void handleStrategy(String strategyType, String parameter) {
        ContextStrategy context = new ContextStrategy();
        switch (strategyType) {
            case "Album":
                context.setStrategy(new AlbumStrategy(spotify));
                break;
            case "Song":
                context.setStrategy(new SongStrategy(spotify));
                break;
            case "Playlist":
                context.setStrategy(new PlaylistStrategy(spotify));
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tipo de estrategia no reconocido.");
                return;
        }
        context.executeStrategy(parameter);
    }

    /**
     * Maneja eventos relacionados con el patrón Template
     */
    public JPanel handleSearchTemplate(String searchType, String name, String searchField) {
        switch (searchType) {
            case "Album":
                return new Album().Search(spotify, name, searchField);
            case "Playlist":
                return new Playlist().Search(spotify, name, searchField);
            case "Track":
                return new Track().Search(spotify, name, searchField);
            default:
                JOptionPane.showMessageDialog(null, "Tipo de búsqueda no reconocido.");
                return null;
        }
    }

    /**
     * Maneja eventos relacionados con el patrón Facade
     */
    public void handleReproduceVideo(String name) {
        VideoReproducer reproducer = new VideoReproducer(spotify, "AIzaSyC4TFkCYgOZeXeAUYpfmM2A6XFMqtRXSqI");
        reproducer.SongReproducer(name);
    }
}
