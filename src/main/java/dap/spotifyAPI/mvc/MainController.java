package dap.spotifyAPI.mvc;

import dap.spotifyAPI.proxy.SpotifyInterface;
import dap.spotifyAPI.utils.Song;

import javax.swing.*;
import java.util.List;

public class MainController {
    private final SpotifyInterface spotify;

    public MainController(SpotifyInterface spotify) {
        this.spotify = spotify;
    }

    public void handleObserverNotification(String artistName) {
        // Lógica para manejar notificaciones de álbumes nuevos
        JOptionPane.showMessageDialog(null, "Notificar nuevos álbumes de: " + artistName);
    }

    public JPanel handleSearchTemplate(String searchType, String name, String searchField) {
        // Lógica para manejar las búsquedas (template pattern)
        switch (searchType) {
            case "Album":
                return new dap.spotifyAPI.template.Album().Search(spotify, name, searchField);
            case "Playlist":
                return new dap.spotifyAPI.template.Playlist().Search(spotify, name, searchField);
            case "Track":
                return new dap.spotifyAPI.template.Track().Search(spotify, name, searchField);
            default:
                JOptionPane.showMessageDialog(null, "Tipo de búsqueda no reconocida.");
                return null;
        }
    }

    public void handleStrategy(String strategyType, String parameter) {
        // Lógica para manejar estrategias
        JOptionPane.showMessageDialog(null, "Ejecutar estrategia: " + strategyType + " con parámetro: " + parameter);
    }
}
