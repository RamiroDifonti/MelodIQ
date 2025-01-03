package dap.spotifyAPI.proxy;

import dap.spotifyAPI.utils.Song;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsAlbumsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
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

            // Crear y ejecutar la solicitud para los álbumes del artista
            GetArtistsAlbumsRequest albumsRequest = _spotifyApi
                    .getArtistsAlbums(artistId)
                    .limit(limit)
                    .offset(offset)
                    .build();
            Paging<AlbumSimplified> albumsPaging = albumsRequest.execute();

            // Agregar los álbumes obtenidos a la lista
            for (AlbumSimplified album : albumsPaging.getItems()) {
                boolean isPrimaryArtist = false;
                for (ArtistSimplified artist2 : album.getArtists()) {
                    if (artistId.equals(artist2.getId())) {
                        isPrimaryArtist = true;
                        break;
                    }
                }
                if (!(album.getAlbumType() == AlbumType.ALBUM)) {
                    isPrimaryArtist = false;
                }
                if (isPrimaryArtist) {
                    allAlbums.add(album);
                }
            }
            return allAlbums;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Song> getTracksByArtist(String artistName) {
        List<Song> tracks = new ArrayList<>();
        SearchItemRequest searchRequest = _spotifyApi.searchItem(artistName, "artist").limit(1).build();
        try {
            Paging<Artist> artistPaging = searchRequest.execute().getArtists();
            Artist artist = artistPaging.getItems()[0];
            String artistId = artist.getId();
            int limit = 25; // Máximo por solicitud
            int offset = 0; // Desplazamiento inicial

            GetArtistsAlbumsRequest albumsRequest = _spotifyApi
                    .getArtistsAlbums(artistId)
                    .limit(limit)
                    .offset(offset)
                    .build();
            Paging<AlbumSimplified> albumsPaging = albumsRequest.execute();

            // Agregar los álbumes obtenidos a la lista
            int limitAlbum = 5; // Máximo por solicitud
            for (AlbumSimplified album : albumsPaging.getItems()) {
                for (ArtistSimplified artist2 : album.getArtists()) {
                    if (artistId.equals(artist2.getId())) {
                        GetAlbumsTracksRequest tracksRequest = _spotifyApi
                                .getAlbumsTracks(album.getId())
                                .limit(limitAlbum)
                                .offset(offset)
                                .build();
                        Paging<TrackSimplified> tracksPaging = tracksRequest.execute();
                        // Agregar los tracks obtenidos
                        TrackSimplified[] items = tracksPaging.getItems();
                        for (TrackSimplified tmp : items) {
                            Track track = _spotifyApi.getTrack(tmp.getId()).build().execute();
                            tracks.add(new Song(track));
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return tracks;
    }

    @Override
    public List<PlaylistSimplified> getPlaylistsByUser(String userId) {
        List<PlaylistSimplified> allPlaylists = new ArrayList<>();
        int limit = 50; // Máximo por solicitud
        int offset = 0; // Desplazamiento inicial

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

        return allPlaylists;
    }

    @Override
    public List<Song> getPlaylistTracks(String playlistId) {
        List<Song> allTracks = new ArrayList<>();
        int limit = 50; // Máximo permitido por solicitud
        int offset = 0;

        try {
            // Crear y ejecutar la solicitud para los items de la playlist
            GetPlaylistsItemsRequest request = _spotifyApi
                    .getPlaylistsItems(playlistId)
                    .limit(limit)
                    .offset(offset)
                    .build();

            Paging<PlaylistTrack> playlistItemsPaging = request.execute();

            // Extraer los tracks de los PlaylistItems y añadirlos a la lista
            for (PlaylistTrack playlistItem : playlistItemsPaging.getItems()) {
                String trackId = playlistItem.getTrack().getId();
                Track track = _spotifyApi.getTrack(trackId).build().execute();
                allTracks.add(new Song(track));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allTracks;
    }

    @Override
    public List<Song> getAlbumTracks(String albumId) {
        List<Song> allTracks = new ArrayList<>();
        int limit = 50; // Máximo por solicitud
        int offset = 0; // Desplazamiento inicial

        try {
            // Crear y ejecutar la solicitud de tracks
            GetAlbumsTracksRequest tracksRequest = _spotifyApi
                    .getAlbumsTracks(albumId)
                    .limit(limit)
                    .offset(offset)
                    .build();
            Paging<TrackSimplified> tracksPaging = tracksRequest.execute();
            // Agregar los tracks obtenidos
            TrackSimplified[] items = tracksPaging.getItems();
            for (TrackSimplified tmp : items) {
                Track track = _spotifyApi.getTrack(tmp.getId()).build().execute();
                allTracks.add(new Song(track));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return allTracks;
    }

    @Override
    public AlbumSimplified getAlbumById(String albumId) {
        try {
            Album album = _spotifyApi.getAlbum(albumId).build().execute();
            return new AlbumSimplified.Builder()
                    .setId(album.getId())
                    .setName(album.getName())
                    .setArtists(album.getArtists())
                    .build();
        } catch (Exception e) {
            System.out.println("Error al obtener el álbum: " + e.getMessage());
            return null;
        }
    }
}
