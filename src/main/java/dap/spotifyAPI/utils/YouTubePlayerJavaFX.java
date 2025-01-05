package dap.spotifyAPI.utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class YouTubePlayerJavaFX extends Application {
    private static String _videoId = "";

    public static void setVideoId(String id) {
        _videoId = id;
    }
    @Override
    public void start(Stage primaryStage) {
        // Video ID de YouTube (ejemplo)

        // URL de YouTube para incrustar el video
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
