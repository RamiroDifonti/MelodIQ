package dap.spotifyAPI.proxy;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsAlbumsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.search.SearchItemRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spotify implements SpotifyInterface {
    private final SpotifyApi _spotifyApi;

    public Spotify(String id, String secret) {
        this._spotifyApi = new SpotifyApi.Builder()
                .setClientId(id)
                .setClientSecret(secret)
                .build();
        try {
            String accessToken = _spotifyApi.clientCredentials().build().execute().getAccessToken();
            _spotifyApi.setAccessToken(accessToken);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public List<AlbumSimplified> getAlbumsByArtist(String artistName) {
        SearchItemRequest searchRequest = _spotifyApi.searchItem(artistName, "artist").limit(1).build();
        try {
            Paging<Artist> artistPaging = searchRequest.execute().getArtists();
            Artist artist = artistPaging.getItems()[0];
            String artistId = artist.getId();
            List<AlbumSimplified> allAlbums = new ArrayList<>();
            int limit = 10; // Máximo por solicitud
            int offset = 0; // Desplazamiento inicial

            while (true) {
                // Crear y ejecutar la solicitud para los álbumes del artista
                GetArtistsAlbumsRequest albumsRequest = _spotifyApi
                        .getArtistsAlbums(artistId)
                        .limit(limit)
                        .offset(offset)
                        .build();
                Paging<AlbumSimplified> albumsPaging = albumsRequest.execute();

                // Agregar los álbumes obtenidos a la lista
                AlbumSimplified[] items = albumsPaging.getItems();
                for (AlbumSimplified album : items) {
                    allAlbums.add(album);
                }

                // Verificar si hay más páginas
                if (albumsPaging.getNext() == null) {
                    break;
                }

                offset += limit;
            }
            return allAlbums;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<TrackSimplified> getTracksByArtist(String artistName) {
        List<AlbumSimplified> albums = getAlbumsByArtist(artistName);
        List<TrackSimplified> tracks = new ArrayList<>();
        for (AlbumSimplified album : albums) {
            int limit = 10;
            int offset = 0;

            while (true) {
                GetAlbumsTracksRequest tracksRequest = _spotifyApi
                        .getAlbumsTracks(album.getId())
                        .limit(limit)
                        .offset(offset)
                        .build();
                Paging<TrackSimplified> trackPaging;
                try {
                    trackPaging = tracksRequest.execute();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }
                tracks.addAll(Arrays.asList(trackPaging.getItems()));

                if (trackPaging.getNext() == null) {
                    break;
                }

                offset += limit;
            }
        }
        return tracks;
    }

    @Override
    public List<PlaylistSimplified> getPlaylistsByUser(String userId) {
        List<PlaylistSimplified> allPlaylists = new ArrayList<>();
        int limit = 10; // Máximo por solicitud
        int offset = 0; // Desplazamiento inicial

        while (true) {
            // Crear y ejecutar la solicitud de playlists
            GetListOfUsersPlaylistsRequest playlistsRequest = _spotifyApi
                    .getListOfUsersPlaylists(userId)
                    .limit(limit)
                    .offset(offset)
                    .build();

            Paging<PlaylistSimplified> playlistsPaging;
            try {
                playlistsPaging = playlistsRequest.execute();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return null;
            }

            // Agregar las playlists obtenidas
            PlaylistSimplified[] items = playlistsPaging.getItems();
            allPlaylists.addAll(Arrays.asList(items));

            // Verificar si hay más páginas
            if (playlistsPaging.getNext() == null) {
                break;
            }

            offset += limit;
        }

        return allPlaylists;
    }

    @Override
    public List<Track> getPlaylistTracks(String playlistId) {
        List<Track> allTracks = new ArrayList<>();
        try {
            Playlist pl = _spotifyApi.getPlaylist(playlistId).build().execute();
            for (PlaylistTrack playlistTrack : pl.getTracks().getItems()) {
                String trackId = playlistTrack.getTrack().getId();
                Track track = _spotifyApi.getTrack(trackId).build().execute();
                allTracks.add(track);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return allTracks;
    }
}
