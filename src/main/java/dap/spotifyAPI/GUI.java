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

class Frame extends JFrame {

    private JPanel _panel;

    // API de Spotify
    private SpotifyApi _spotifyApi;

    // Factoría e información de la playlist
    private PlaylistFactory _factory;
    private int _amount;
    private String _playlistName = "Default";
    private Boolean[] _genres = new Boolean[]{false, false, false};

    public Frame(SpotifyApi spotifyApi) {
        _spotifyApi = spotifyApi;
        Start();
    }
    public void Start() {
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

        // Tipos de genero
        JLabel genreTitle = new JLabel("Seleccione los tipos de género que quieres tener en la playlist:");
        genreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(genreTitle);
        // Crear tres JCheckBox
        JPanel genres = new JPanel();
        genres.setLayout(new BoxLayout(genres, BoxLayout.X_AXIS));
        JCheckBox pop = new JCheckBox("Pop");
        JCheckBox jazz = new JCheckBox("Jazz");
        JCheckBox reggaeton = new JCheckBox("Reggaeton");
        genres.add(pop);
        genres.add(jazz);
        genres.add(reggaeton);
        _panel.add(genres);

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
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna opción.");
                }
                if (pop.isSelected()) {
                    _genres[0] = true;
                }
                if (jazz.isSelected()) {
                    _genres[1] = true;
                }
                if (reggaeton.isSelected()) {
                    _genres[2] = true;
                }
                if (!_genres[0] && !_genres[1] && !_genres[2]) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ningún género.");
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
    public void createPlaylist() {
        // Eliminar y limpiar jframe
        remove(_panel);
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.Y_AXIS));
        _panel.setBorder(new EmptyBorder(40, 20, 20, 20));

        // Llamar a los productos
        JazzProduct jp = _factory.createJazz();
        PopProduct pp = _factory.createPop();
        ReggaetonProduct rp = _factory.createReggaeton();

        // Canciones de cada género
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
        JPanel songsPanel = new JPanel();
        songsPanel.setLayout(new BoxLayout(songsPanel, BoxLayout.Y_AXIS));
        if (_genres[0]) {
            List <Song> popSongs = new ArrayList<>();
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
            List <Song> jazzSongs = new ArrayList<>();
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
            List <Song> reggaetonSongs = new ArrayList<>();
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
        // Panel scroll que se mueve de arriba a abajo
        JScrollPane scrollPane = new JScrollPane(songsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Titulo de la playlist
        JLabel playlistTitle = new JLabel(_playlistName);
        playlistTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(playlistTitle);
        _panel.add(scrollPane);

        // Botón para volver
        // Generar playlist
        JButton ret = new JButton("Volver");
        ret.setAlignmentX(Component.CENTER_ALIGNMENT);
        _panel.add(ret);

        ret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(_panel);
                _amount = 0;
                _playlistName = "Default";
                _genres = new Boolean[]{false, false, false};
                Start();
            }
        });
        add(_panel);
        revalidate();
        repaint();
    }
}

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
            // Utiliza el token de acceso para hacer otras solicitudes
            spotifyApi.setAccessToken(accessToken);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        new Frame(spotifyApi);
    }
}