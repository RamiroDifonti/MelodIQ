package dap.spotifyAPI;

//import dap.spotifyAPI.abstractfactory.PlaylistFactory;
//import dap.spotifyAPI.abstractfactory.SportFactory;
//import dap.spotifyAPI.abstractfactory.StudyFactory;
//import dap.spotifyAPI.abstractfactory.PartyFactory;
//
//import dap.spotifyAPI.products.jazz.JazzProduct;
//import dap.spotifyAPI.products.pop.PopProduct;
//import dap.spotifyAPI.products.reggaeton.ReggaetonProduct;

import dap.spotifyAPI.utils.Song;

import org.apache.hc.core5.http.ParseException;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase principal de la interfaz gráfica de usuario (GUI) que interactúa con la API de Spotify.
 * Esta clase configura la autenticación a través de la API de Spotify utilizando las credenciales
 * del cliente, obtiene un token de acceso y pasa dicho token a un objeto {@link SpotifyApi} para
 * realizar futuras solicitudes.
 */
public class GUI {

    /**
     * Método principal que configura la autenticación con la API de Spotify y lanza la interfaz gráfica.
     *
     * @param args Argumentos de línea de comandos (no utilizados en esta implementación).
     */
    public static void main(String[] args) {
        // Identificador de cliente proporcionado por Spotify para la autenticación
        String client_id = "75399c2bdb7948b882f6647795204070";

        // Secreto de cliente proporcionado por Spotify para la autenticación
        String client_secret = "484b0ac5151f4288a40c06e8ae7a4dc4";

        // Construcción de la instancia de la API de Spotify utilizando las credenciales del cliente
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(client_id)
                .setClientSecret(client_secret)
                .build();

        // Construcción de la solicitud de credenciales del cliente para obtener el token de acceso
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

        try {
            // Obtención del token de acceso y configuración de la API de Spotify para realizar solicitudes
            String accessToken = clientCredentialsRequest.execute().getAccessToken();
            spotifyApi.setAccessToken(accessToken); // Establece el token de acceso en el objeto spotifyApi
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            // Manejo de errores durante la obtención del token de acceso
            System.out.println("Error: " + e.getMessage());
        }

        // Crea y muestra la interfaz gráfica de usuario con el objeto spotifyApi
        new Frame(spotifyApi);
    }
}
