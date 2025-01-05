package dap.spotifyAPI.utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;

public class YouTubePlayerJavaFX extends Application {
    private static String _videoId = "";

    public static void setVideoId(String id) {
        _videoId = id;
    }
    @Override
    public void start(Stage primaryStage) {
        // Intentar abrirlo en chrome
        if (Desktop.isDesktopSupported()) {
            try {
                // URL del video en YouTube
                String youtubeUrl = "https://www.youtube.com/watch?v=" + _videoId;

                // Usar Desktop para abrir la URL en el navegador predeterminado
                Desktop.getDesktop().browse(new URI(youtubeUrl));

                // Puedes agregar alguna lógica adicional si necesitas hacer algo más con JavaFX.
            } catch (Exception e) {
                e.printStackTrace();
            }
        // Si no se puede abrir en el navegador, abrirlo en un WebView
        } else {
            String youtubeUrl = "https://www.youtube.com/embed/" + _videoId + "?autoplay=1";  // 'autoplay=1' para reproducción automática

            // Crear el componente WebView
            WebView webView = new WebView();

            // Cargar el video en el WebView
            webView.getEngine().load(youtubeUrl);

            // Crear la escena con el WebView
            Scene scene = new Scene(webView, 800, 600); // Tamaño de la ventana

            // Configurar la ventana principal (Stage)
            primaryStage.setTitle("Reproductor de YouTube");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}
