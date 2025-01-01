package dap.spotifyAPI.proxy;

import se.michaelthelin.spotify.model_objects.specification.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Proxy implements SpotifyInterface {
    private final SpotifyInterface _spotify;
    private final Map<String, List<AlbumSimplified>> _albumcache = new HashMap<>();
    //private final Map<String, Track> _trackcache = new HashMap<>();
    private final Map<String, List<PlaylistSimplified>> _playlistcache = new HashMap<>();

    public Proxy(SpotifyInterface spotify) {
        this._spotify = spotify;
    }
    
    @Override
    public List<AlbumSimplified> getAlbumsByArtist(String artistId) {
        List<AlbumSimplified> albums = _albumcache.get(artistId);

        if (albums != null) {
            for (AlbumSimplified album : albums) {
                if (album.getId().equals(albumId)) {
                    return List.of(album);
                }
            }
            return _albumcache.get(albumId);
        } else {
            List<AlbumSimplified> albums = _spotify.getAlbumByArtist(artistId, albumId);
            _albumcache.put(albumId, albums);
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
        if (_playlistcache.containsKey(playlistId)) {
            return _playlistcache.get(playlistId);
        } else {
            List<PlaylistSimplified> playlists = _spotify.getPlaylistByUser(userId, playlistId);
            _playlistcache.put(playlistId, playlists);
            return playlists;
        }
    }
}
