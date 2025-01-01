package dap.spotifyAPI.proxy;

import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Proxy implements SpotifyInterface {
    private final SpotifyInterface _spotify;
    private final Map<String, List<AlbumSimplified>> _albumcache = new HashMap<>();
    private final Map<String, List<TrackSimplified>> _trackcache = new HashMap<>();
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
    public List<TrackSimplified> getTracksByArtist(String artistId) {
        List<TrackSimplified> tracks = _trackcache.get(artistId);
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
}
