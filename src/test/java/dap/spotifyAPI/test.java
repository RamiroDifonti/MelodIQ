package test;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsAlbumsRequest;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import com.neovisionaries.i18n.CountryCode;

import java.io.IOException;

public class test {
    private static final Logger logger = LoggerFactory.getLogger(test.class);
    public static void main(String[] args) throws IOException, ParseException, SpotifyWebApiException {
        // For all requests an access token is needed
/*        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken("taHZ2SdB-bPA3FsK3D7ZN5npZS47cMy-IEySVEGttOhXmqaVAIo0ESvTCLjLBifhHOHOIuhFUKPW1WMDP7w6dj3MAZdWT8CLI2MkZaXbYLTeoDvXesf2eeiLYPBGdx8tIwQJKgV8XdnzH_DONk")
                .build();*/
        String client_id = "75399c2bdb7948b882f6647795204070";
        String client_secret = "484b0ac5151f4288a40c06e8ae7a4dc4";
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(client_id)
                .setClientSecret(client_secret)
                .build();
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            String accessToken = clientCredentialsRequest.execute().getAccessToken();
            System.out.println("Token de acceso: " + accessToken);

            // Utiliza el token de acceso para hacer otras solicitudes
            spotifyApi.setAccessToken(accessToken);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Enlace a Bad Bunny:
        // https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X?si=db397108ad8c49c6
        String artistId = "4q3ewBCX7sLwd24euuV69X";
        GetArtistsAlbumsRequest albums = spotifyApi.getArtistsAlbums(artistId).build();

        for (AlbumSimplified album : albums.execute().getItems()) {
            System.out.println(album.getName());
        }
        String genero = "rock";
        int limite = 10;
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks("genre:\"" + genero + "\"")
                .limit(limite)
                .build();

        try {
            // Realizar la solicitud y obtener los resultados
            Paging<Track> trackPaging = searchTracksRequest.execute();

            // Procesar y mostrar los resultados
            Track[] tracks = trackPaging.getItems();
            for (int i = 0; i < tracks.length; i++) {
                Track track = tracks[i];
                System.out.println((i + 1) + ". " + track.getName() + " - " +
                        track.getArtists()[0].getName() +
                        " (Album: " + track.getAlbum().getName() + ")");
                System.out.println("   URL: " + track.getExternalUrls().getExternalUrls().get("spotify"));
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            logger.info("¡Hola, Spotify!");
        }
        logger.debug("Este es un mensaje de debug.");
    }
}