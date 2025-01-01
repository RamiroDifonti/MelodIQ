package dap.spotifyAPI.proxy;

import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Proxy implements SpotifyInterface {
    private final SpotifyInterface _spotify;
    private final Map<String, List<AlbumSimplified>> _albumcache = new HashMap<>();
    private final Map<String, Track> _trackcache = new HashMap<>();
    private final Map<String, List<PlaylistSimplified>> _playlistcache = new HashMap<>();

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
    public Track getTracksByArtist(String artistId) {
        if (_trackcache.containsKey(trackId)) {
            return _trackcache.get(trackId);
        } else {
            Track track = _spotify.getTrack(trackId);
            _trackcache.put(trackId, track);
            return track;
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
}
