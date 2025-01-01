package dap.spotifyAPI.proxy;

import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.HashMap;
import java.util.Map;

public class Proxy implements SpotifyInterface {
    private final SpotifyInterface _spotify;
    private final Map<String, Album> _albumcache = new HashMap<>();
    private final Map<String, Track> _trackcache = new HashMap<>();
    private final Map<String, Playlist> _playlistcache = new HashMap<>();
    private final Map<String, User> _usercache = new HashMap<>();
    private final Map<String, Artist> _artistcache = new HashMap<>();

    public Proxy(SpotifyInterface spotify) {
        this._spotify = spotify;
    }
    
    @Override
    public Album getAlbum(String albumId) {
        if (_albumcache.containsKey(albumId)) {
            return _albumcache.get(albumId);
        } else {
            Album album = _spotify.getAlbum(albumId);
            _albumcache.put(albumId, album);
            return album;
        }
    }

    @Override
    public Track getTrack(String trackId) {
        if (_trackcache.containsKey(trackId)) {
            return _trackcache.get(trackId);
        } else {
            Track track = _spotify.getTrack(trackId);
            _trackcache.put(trackId, track);
            return track;
        }
    }

    @Override
    public Playlist getPlaylist(String playlistId) {
        if (_playlistcache.containsKey(playlistId)) {
            return _playlistcache.get(playlistId);
        } else {
            Playlist playlist = _spotify.getPlaylist(playlistId);
            _playlistcache.put(playlistId, playlist);
            return playlist;
        }
    }

    @Override
    public User getUser(String userId) {
        if (_usercache.containsKey(userId)) {
            return _usercache.get(userId);
        } else {
            User user = _spotify.getUser(userId);
            _usercache.put(userId, user);
            return user;
        }
    }

    @Override
    public Artist getArtist(String artistId) {
        if (_artistcache.containsKey(artistId)) {
            return _artistcache.get(artistId);
        } else {
            Artist artist = _spotify.getArtist(artistId);
            _artistcache.put(artistId, artist);
            return artist;
        }
    }
}
