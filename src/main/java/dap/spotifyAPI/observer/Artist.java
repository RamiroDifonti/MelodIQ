package dap.spotifyAPI.observer;

import dap.spotifyAPI.proxy.SpotifyInterface;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Artist extends Subject {
    private SpotifyInterface spotify;
    private String artistName;
    private List<AlbumSimplified> albums;

    public Artist(SpotifyInterface spotify, String artistName) {
        this.spotify = spotify;
        this.artistName = artistName;
        this.albums = new ArrayList<>();
    }

    public void fetchAlbums() {
        List<AlbumSimplified> newAlbums = spotify.getAlbumsByArtist(artistName);
        if (newAlbums == null) return;

        for (AlbumSimplified album : newAlbums) {
            if (!albums.contains(album)) {
                albums.add(album);
                notifyObservers("Nuevo álbum de " + artistName + ": " + album.getName());
            }
        }
    }

    public void addAlbum(String albumId) {
        AlbumSimplified album = spotify.getAlbumById(albumId);
        if (album != null && !albums.contains(album)) {
            albums.add(album);
            notifyObservers("Álbum agregado: " + album.getName());
        } else {
            JOptionPane.showMessageDialog(null, "Álbum no encontrado o ya existente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<AlbumSimplified> getLatestAlbums(int count) {
        return albums.subList(Math.max(albums.size() - count, 0), albums.size());
    }
}