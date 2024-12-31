package dap.spotifyAPI.proxy;

import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.HashMap;
import java.util.Map;

public class Proxy implements SpotifyInterface {
    private final SpotifyInterface _spotify;
    private final Map<String, Album> _albumcache;
    private final Map<String, Track> _trackcache;
    private final Map<String, Playlist> _playlistcache;
    
    public Proxy(SpotifyInterface spotify) {
        this._spotify = spotify;
        this._albumcache = new HashMap<>();
        this._trackcache = new HashMap<>();
        this._playlistcache = new HashMap<>();
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
}
