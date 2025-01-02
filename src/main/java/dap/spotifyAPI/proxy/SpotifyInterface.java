package dap.spotifyAPI.proxy;

import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.List;

public interface SpotifyInterface {
    List<AlbumSimplified> getAlbumsByArtist(String artistId);
    List<Song> getTracksByArtist(String artistId);
    List<PlaylistSimplified> getPlaylistsByUser(String userId);
    List<Song> getPlaylistTracks(String playlistId);
    List<Song> getAlbumTracks(String albumId);
}

// 1. Añadir canciones al subject (las antiguas)
// 2. Cada x tiempo hacer una llama de getTracksByArtist a la API
// 3. Añadir canciones nuevas al subject (IMPORTANTE NO AÑADIR DUPLICADOS)
// 4. Notificar a los observadores