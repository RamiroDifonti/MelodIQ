package dap.spotifyAPI;

import abstractfactory.PartyFactory;
import abstractfactory.PlaylistFactory;
import abstractfactory.SportFactory;
import abstractfactory.StudyFactory;
import org.apache.hc.core5.http.ParseException;
import products.jazz.JazzProduct;
import products.pop.PopProduct;
import products.reggaetton.ReggaettonProduct;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class Frame extends JFrame {

    private JPanel _panel;

    // API de Spotify
    private SpotifyApi _spotifyApi;

    // Factoría e información de la playlist
    private PlaylistFactory _factory;
    private int _amount;
    private String _playlistName = "Default";

    public Frame(SpotifyApi spotifyApi) {
        _spotifyApi = spotifyApi;
        setLayout(new FlowLayout());
        // Crear un BoxLayout para ir introduciendo los objetos
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
        // Titulo
        JLabel title = new JLabel("Seleccione la actividad que desea realizar:");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(title);
        // Tipo de actividad
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
        // Número de canciones de la playlist
        JLabel songTitle = new JLabel("Seleccione el número de canciones de la playlist:");
        songTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(songTitle);
        JTextField numberField = new JTextField(10);
        numberField.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(numberField);

/*        // Tipos de genero
        JLabel genreTitle = new JLabel("Seleccione los tipos de género que quieres tener en la playlist:");
        genreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(genreTitle);
        // Crear tres JCheckBox
        JCheckBox pop = new JCheckBox("Botón 1");
        JCheckBox jazz = new JCheckBox("Botón 2");
        JCheckBox reggaeton = new JCheckBox("Botón 3");

        // Añadir los JCheckBox al JFrame
        add(pop);
        add(jazz);
        add(reggaeton);*/


        // Generar playlist
        JButton confirm = new JButton("Generar");
        confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(confirm);

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                if(!playlistField.getText().isEmpty()) {
                    _playlistName = playlistField.getText();
                }

                if (sport.isSelected()) {
                    _factory = new SportFactory();
                } else if (study.isSelected()) {
                    _factory = new StudyFactory();
                } else if (party.isSelected() ) {
                    _factory = new PartyFactory();
                } else {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna opción");
                }
            }
        });
        add(_panel);
        setSize(1000, 850);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public void createPlaylist() {
        JazzProduct jp = _factory.addJazz();
        PopProduct pp = _factory.addPop();
        ReggaettonProduct rp = _factory.addReggaetton();

    }
}

// archivos para hacer testing:
// xml: https://www.w3schools.com/xml/simple.xml
// json: https://microsoftedge.github.io/Demos/json-dummy-data/64KB-min.json
// csv: https://people.sc.fsu.edu/~jburkardt/data/csv/ford_escort.csv

public class GUI {
    public static void main(String[] args) {
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
        new Frame(spotifyApi);
    }
}