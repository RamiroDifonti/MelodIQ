package dap.spotifyAPI;

import dap.spotifyAPI.abstractfactory.PlaylistFactory;
import dap.spotifyAPI.abstractfactory.SportFactory;
import dap.spotifyAPI.abstractfactory.StudyFactory;
import dap.spotifyAPI.abstractfactory.PartyFactory;

import dap.spotifyAPI.products.jazz.JazzProduct;
import dap.spotifyAPI.products.pop.PopProduct;
import dap.spotifyAPI.products.reggaeton.ReggaetonProduct;

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
 * Clase que representa la interfaz gráfica de usuario para la creación de una playlist personalizada
 * basada en géneros y actividades utilizando la API de Spotify.
 *
 * Esta clase se encarga de la configuración inicial de la interfaz gráfica, la recopilación de los datos
 * ingresados por el usuario, y la creación de la playlist mediante una factoría de productos (géneros de música).
 */
class Frame extends JFrame {

    private JPanel _panel;

    // API de Spotify
    private SpotifyApi _spotifyApi;

    // Factoría e información de la playlist
    private PlaylistFactory _factory;
    private int _amount;
    private String _playlistName = "Default";
    private Boolean[] _genres = new Boolean[]{false, false, false};

    /**
     * Constructor de la clase Frame que inicializa la API de Spotify y llama al método de configuración inicial.
     *
     * @param spotifyApi Instancia de la API de Spotify que se usará para generar la playlist.
     */
    public Frame(SpotifyApi spotifyApi) {
        _spotifyApi = spotifyApi;
        Start();
    }

    /**
     * Método que configura la interfaz gráfica de usuario, donde se recogen los datos del usuario como el nombre
     * de la playlist, la actividad, el número de canciones y los géneros seleccionados.
     */
    public void Start() {
        setLayout(new FlowLayout());

        // Crear un panel con un BoxLayout para agregar los componentes verticalmente
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.Y_AXIS));
        _panel.setBorder(new EmptyBorder(40, 20, 20, 20));

        // Nombre de la playlist
        JLabel name = new JLabel("Introduzca el nombre de la playlist:");
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(name);
        JTextField playlistField = new JTextField("");
        playlistField.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(playlistField);

        // Título de actividad
        JLabel title = new JLabel("Seleccione la actividad que desea realizar:");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(title);

        // Selección de tipo de actividad (Deporte, Estudio, Fiesta)
        JPanel activities = new JPanel();
        activities.setLayout(new BoxLayout(activities, BoxLayout.X_AXIS));
        JRadioButton sport = new JRadioButton("Deporte");
        JRadioButton study = new JRadioButton("Estudio");
        JRadioButton party = new JRadioButton("Fiesta");
        activities.add(sport);
        activities.add(study);
        activities.add(party);
        _panel.add(activities);

        ButtonGroup options = new ButtonGroup();
        options.add(sport);
        options.add(study);
        options.add(party);

        // Número de canciones
        JLabel songTitle = new JLabel("Seleccione el número de canciones de la playlist:");
        songTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(songTitle);
        JTextField numberField = new JTextField(10);
        numberField.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(numberField);

        // Géneros de la playlist
        JLabel genreTitle = new JLabel("Seleccione los tipos de género que quieres tener en la playlist:");
        genreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(genreTitle);

        // Selección de géneros (Pop, Jazz, Reggaeton)
        JPanel genres = new JPanel();
        genres.setLayout(new BoxLayout(genres, BoxLayout.X_AXIS));
        JCheckBox pop = new JCheckBox("Pop");
        JCheckBox jazz = new JCheckBox("Jazz");
        JCheckBox reggaeton = new JCheckBox("Reggaeton");
        genres.add(pop);
        genres.add(jazz);
        genres.add(reggaeton);
        _panel.add(genres);

        // Botón para generar la playlist
        JButton confirm = new JButton("Generar");
        confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(confirm);

        // Acción del botón para generar la playlist
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validación del número de canciones
                if (!numberField.getText().isEmpty() && numberField.getText().matches("\\d*")) {
                    int numSongs = Integer.parseInt(numberField.getText());
                    if (numSongs <= 0) {
                        JOptionPane.showMessageDialog(null, "El número de canciones no puede ser menor que cero.");
                        return;
                    }
                    _amount = numSongs;
                } else {
                    JOptionPane.showMessageDialog(null, "No has escrito un número valido de canciones.");
                    return;
                }

                // Actualización del nombre de la playlist si se ha ingresado un valor
                if(!playlistField.getText().isEmpty()) {
                    _playlistName = playlistField.getText();
                }

                // Determinación de la factoría en base a la actividad seleccionada
                if (sport.isSelected()) {
                    _factory = new SportFactory();
                } else if (study.isSelected()) {
                    _factory = new StudyFactory();
                } else if (party.isSelected() ) {
                    _factory = new PartyFactory();
                } else {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna opción.");
                    return;
                }

                // Actualización de los géneros seleccionados
                if (pop.isSelected()) {
                    _genres[0] = true;
                }
                if (jazz.isSelected()) {
                    _genres[1] = true;
                }
                if (reggaeton.isSelected()) {
                    _genres[2] = true;
                }

                // Validación de la selección de géneros
                if (!_genres[0] && !_genres[1] && !_genres[2]) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ningún género.");
                    return;
                }
                createPlaylist();
            }
        });

        add(_panel);
        revalidate();
        repaint();
        setSize(1000, 850);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Método que genera la playlist con los géneros seleccionados y el número de canciones especificado.
     * Llama a los productos de cada factoría para crear las canciones de cada género.
     */
    public void createPlaylist() {
        // Eliminar y limpiar jframe
        remove(_panel);
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.Y_AXIS));
        _panel.setBorder(new EmptyBorder(40, 20, 20, 20));

        // Llamar a los productos de la factoría
        JazzProduct jp = _factory.createJazz();
        PopProduct pp = _factory.createPop();
        ReggaetonProduct rp = _factory.createReggaeton();

        // Cálculo de la distribución de canciones entre géneros
        int sumGenres = 0;
        for (Boolean genre : _genres) {
            if (genre) {
                ++sumGenres;
            }
        }
        int numSongs[] = new int[] {0, 0, 0};
        for (int i = 0; i < 3; ++i) {
            if (_genres[i]) {
                numSongs[i] = _amount / sumGenres;
            }
        }
        if (_amount % sumGenres != 0) {
            for (int i = 0; i < 3; ++i) {
                if (numSongs[i] != 0) {
                    numSongs[i] += _amount % sumGenres;
                }
            }
        }

        // Crear el panel de canciones
        JPanel songsPanel = new JPanel();
        songsPanel.setLayout(new BoxLayout(songsPanel, BoxLayout.Y_AXIS));

        // Agregar canciones de Pop, Jazz y Reggaeton a la lista de canciones
        if (_genres[0]) {
            List<Song> popSongs = new ArrayList<>();
            try {
                popSongs = pp.create(_spotifyApi, numSongs[0]);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No se han encontrado canciones de Pop.");
                remove(_panel);
                _amount = 0;
                _playlistName = "Default";
                _genres = new Boolean[]{false, false, false};
                Start();
                return;
            }
            for (Song song : popSongs) {
                songsPanel.add(song.getLayout());
            }
        }
        if (_genres[1]) {
            List<Song> jazzSongs = new ArrayList<>();
            try {
                jazzSongs = jp.create(_spotifyApi, numSongs[1]);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No se han encontrado canciones de Jazz.");
                remove(_panel);
                _amount = 0;
                _playlistName = "Default";
                _genres = new Boolean[]{false, false, false};
                Start();
                return;
            }
            for (Song song : jazzSongs) {
                songsPanel.add(song.getLayout());
            }
        }
        if (_genres[2]) {
            List<Song> reggaetonSongs = new ArrayList<>();
            try {
                reggaetonSongs = rp.create(_spotifyApi, numSongs[2]);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No se han encontrado canciones de Reggaeton.");
                remove(_panel);
                _amount = 0;
                _playlistName = "Default";
                _genres = new Boolean[]{false, false, false};
                Start();
                return;
            }
            for (Song song : reggaetonSongs) {
                songsPanel.add(song.getLayout());
            }
        }

        _panel.add(songsPanel);
        add(_panel);
        revalidate();
        repaint();
    }
}

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
