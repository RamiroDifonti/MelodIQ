package dap.spotifyAPI.observer;

import dap.spotifyAPI.proxy.SpotifyInterface;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

import java.util.ArrayList;
import java.util.List;

public class Artist extends Subject {
    private final SpotifyInterface spotify;
    private final String name;
    private final List<AlbumSimplified> albums;

    public Artist(SpotifyInterface spotify, String name) {
        this.spotify = spotify;
        this.name = name;
        this.albums = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    /**
     * Devuelve una lista de los últimos álbumes del artista.
     *
     * @param count Número máximo de álbumes a devolver.
     * @return Lista de los álbumes más recientes.
     */
    public List<AlbumSimplified> getLatestAlbums(int count) {
        if (albums.isEmpty()) {
            fetchAlbums();
        }
        return albums.subList(0, Math.min(count, albums.size()));
    }

    /**
     * Obtiene álbumes del artista desde Spotify y notifica si hay nuevos álbumes.
     */
    public void fetchAlbums() {
        List<AlbumSimplified> previousAlbums = new ArrayList<>(albums);
        albums.clear();
        AlbumSimplified[] fetchedAlbums = spotify.getAlbumsByArtist(name).toArray(new AlbumSimplified[0]);
        if (fetchedAlbums != null) {
            for (AlbumSimplified album : fetchedAlbums) {
                albums.add(album);
            }
        }
        // Notificar solo si hay álbumes nuevos
        for (AlbumSimplified album : albums) {
            if (!previousAlbums.contains(album)) {
                notifyObservers("Nuevo álbum: " + album.getName() + " del artista " + name);
            }
        }
    }

    /**
     * Agrega un nuevo álbum al artista y notifica a los observadores.
     *
     * @param albumId ID del álbum a agregar.
     */
    public void addAlbum(String albumId) {
        AlbumSimplified album = spotify.getAlbumById(albumId);
        if (album != null) {
            albums.add(0, album); // Agregar al inicio para mantener los más recientes primero
            notifyObservers("El artista " + album.getArtists()[0].getName() + " ha agregado un nuevo álbum: " + album.getName());
        }
    }
}
