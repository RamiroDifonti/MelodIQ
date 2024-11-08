package dap.spotifyAPI.utils;


import se.michaelthelin.spotify.model_objects.specification.Track;

import javax.swing.*;
import java.awt.*;

// Clase que representa una canción, tiene:
// - portada de la canción
// - nombre de la canción
// - nombre del artista
// - duración de la canción ¿?
// - url de la canción (revisar api spotify)
public class Song {
    JPanel _cover;
    public Song(Track song) {
        _cover = new JPanel();
        _cover.setLayout(new BoxLayout(_cover, BoxLayout.X_AXIS));
    }
    public JPanel getLayout() {
        return _cover;
    }
}
