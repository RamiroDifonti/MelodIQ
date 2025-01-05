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
    AlbumSimplified getAlbumById(String albumId);
}