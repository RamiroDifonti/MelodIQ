package dap.spotifyAPI.utils;

import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import javax.swing.*;
import java.awt.*;

import java.net.URL;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.event.*;

/**
 * Representa una canción dentro de la interfaz de usuario, mostrando su portada, nombre y artista.
 * Proporciona la funcionalidad de visualizar la información de la canción y permitir la interacción
 * para abrir la URL correspondiente de la canción en un navegador.
 */
public class Song {
  /** Panel contenedor de la información de la canción */
  private JPanel _cover;

  /** Etiqueta que muestra el nombre de la canción */
  private JLabel _songNameLabel;

  /** Etiqueta que muestra el nombre del artista(s) */
  private JLabel _artistsLabel;

  /** Etiqueta que muestra la portada del álbum de la canción */
  private JLabel _albumCoverLabel;

  /** Nombre de la canción */
  private String _name;

  /**
   * Constructor de la clase Song.
   * Este constructor inicializa los componentes visuales necesarios para mostrar la información
   * de una canción, incluyendo su nombre, los artistas y la portada del álbum.
   *
   * @param song El objeto Track que contiene la información de la canción.
   */
  public Song(Track song) {
    _name = song.getName();

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

  /**
   * Obtiene el panel principal que contiene la información visual de la canción.
   *
   * @return El panel con la información de la canción, incluyendo la portada, nombre y artista.
   */
  public JPanel getLayout() {
    return _cover;
  }
  public String getName() {
    return _name;
  }
}
