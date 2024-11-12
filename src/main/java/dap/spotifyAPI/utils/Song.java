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
import java.net.URL;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class Song {
    private JPanel _cover;
    private JLabel _songNameLabel;
    private JLabel _artistsLabel;
    private JLabel _albumCoverLabel;

    public Song(Track song) {
        _cover = new JPanel();
        _cover.setLayout(new BoxLayout(_cover, BoxLayout.Y_AXIS)); // Layout vertical para las canciones

        // Cargar datos de la canción
        String songName = song.getName();
        String artists = String.join(", ", song.getArtists()[0].getName());
        String albumCoverUrl = song.getAlbum().getImages()[0].getUrl();

        // Crear y agregar la imagen de portada de la canción
        _albumCoverLabel = new JLabel();
        try {
            // Cargar y redimensionar la imagen a 64x64 píxeles
            BufferedImage albumCoverImage = ImageIO.read(new URL(albumCoverUrl));
            Image scaledImage = albumCoverImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            _albumCoverLabel.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            System.err.println("Error loading album cover image: " + e.getMessage());
        }

        // Crear un panel de GridLayout para el nombre de la canción y los artistas
        JPanel songInfoPanel = new JPanel();
        songInfoPanel.setLayout(new BoxLayout(songInfoPanel, BoxLayout.Y_AXIS));

        // Nombre de la canción en negrita
        _songNameLabel = new JLabel("<html><b>" + songName + "</b></html>");

        // Nombre del artista
        _artistsLabel = new JLabel(artists);

        // Agregar los componentes al panel
        songInfoPanel.add(_songNameLabel);
        songInfoPanel.add(_artistsLabel);

        // Crear un contenedor para la imagen y la información
        JPanel songContainer = new JPanel();
        songContainer.setLayout(new BoxLayout(songContainer, BoxLayout.X_AXIS)); // Layout horizontal
        songContainer.add(_albumCoverLabel); // Imagen de la portada
        songContainer.add(Box.createRigidArea(new Dimension(10, 0))); // Espacio entre la imagen y el texto
        songContainer.add(songInfoPanel); // Información de la canción (nombre y artista)

        // Añadir la canción al panel principal
        _cover.add(songContainer);

        // Añadir un separador (divisor) entre las canciones
        _cover.add(Box.createVerticalStrut(10)); // Esto añade un espacio entre las canciones

        // Si deseas hacer clic en el nombre de la canción para abrir un enlace (ejemplo: abrir en Spotify o similar)
        _songNameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Abre la URL correspondiente en el navegador
                try {
                    Desktop.getDesktop().browse(new URL(song.getExternalUrls().get("spotify")).toURI());
                } catch (Exception ex) {
                    System.err.println("Error opening URL: " + ex.getMessage());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar el cursor al pasar sobre el nombre de la canción
                _songNameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }

    public JPanel getLayout() {
        return _cover;
    }
}


