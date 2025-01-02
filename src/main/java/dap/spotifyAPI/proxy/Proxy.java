package dap.spotifyAPI.proxy;

import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Proxy implements SpotifyInterface {
    private final SpotifyInterface _spotify;
    private final Map<String, List<AlbumSimplified>> _albumcache = new HashMap<>();
    private final Map<String, List<Song>> _trackcache = new HashMap<>();
    private final Map<String, List<PlaylistSimplified>> _playlistcache = new HashMap<>();
    private final Map<String, List<Song>> _playlistTracksCache = new HashMap<>();
    private final Map<String, List<Song>> _albumTracksCache = new HashMap<>();

    public Proxy(SpotifyInterface spotify) {
        this._spotify = spotify;
    }
    
    @Override
    public List<AlbumSimplified> getAlbumsByArtist(String artistId) {
        List<AlbumSimplified> albums = _albumcache.get(artistId);
        if (albums != null) {
            return albums;
        } else {
            albums = _spotify.getAlbumsByArtist(artistId);
            _albumcache.put(artistId, albums);
            return albums;
        }
    }

    @Override
    public List<Song> getTracksByArtist(String artistId) {
        List<Song> tracks = _trackcache.get(artistId);
        if (tracks != null) {
            return tracks;
        } else {
            tracks = _spotify.getTracksByArtist(artistId);
            _trackcache.put(artistId, tracks);
            return tracks;
        }
    }

    @Override
    public List<PlaylistSimplified> getPlaylistsByUser(String userId) {
        List<PlaylistSimplified> playlists = _spotify.getPlaylistsByUser(userId);
        if (playlists != null) {
            return playlists;
        } else {
            playlists = _spotify.getPlaylistsByUser(userId);
            _playlistcache.put(userId, playlists);
            return playlists;
        }
    }

    @Override
    public List<Song> getPlaylistTracks(String playlistId) {
        List<Song> tracks = _playlistTracksCache.get(playlistId);
        if (tracks != null) {
            return tracks;
        } else {
            tracks = _spotify.getPlaylistTracks(playlistId);
            _playlistTracksCache.put(playlistId, tracks);
            return tracks;
        }
    }

    @Override
    public List<Song> getAlbumTracks(String albumId) {
        List<Song> tracks = _albumTracksCache.get(albumId);
        if (tracks != null) {
            return tracks;
        } else {
            tracks = _spotify.getAlbumTracks(albumId);
            _albumTracksCache.put(albumId, tracks);
            return tracks;
        }
    }
}
