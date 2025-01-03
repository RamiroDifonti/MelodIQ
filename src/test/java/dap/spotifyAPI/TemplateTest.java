package dap.spotifyAPI;

import dap.spotifyAPI.factoryMethod.*;
import dap.spotifyAPI.proxy.Proxy;
import dap.spotifyAPI.proxy.Spotify;
import dap.spotifyAPI.proxy.SpotifyInterface;

import javax.swing.*;
import java.awt.*;

public class TemplateTest extends JFrame {
    private final SpotifyInterface _manager;
    public TemplateTest() {
        String client_id = "c9072e3391954da9b7caf8cdcb0e8d34";
        String client_secret = "acbab08c63964194bf8989dc832b2947";

        SpotifyInterface spotify = new Spotify(client_id, client_secret);
        _manager = new Proxy(spotify);

        setTitle("MelodIQ");
        setSize(1920, 1080);
        setLayout(new GridLayout(2, 2));

        add(observer());
        add(template());
        add(mediator());
        add(strategy());

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public JPanel template() {
        PatternFactory factory = new TemplateFactory();
        PatternProduct product = factory.createPattern();

        return product.display(_manager);
    }
    public JPanel mediator() {
        PatternFactory factory = new MediatorFactory();
        PatternProduct product = factory.createPattern();

        return product.display(_manager);
    }
    public JPanel observer() {
        PatternFactory factory = new ObserverFactory();
        PatternProduct product = factory.createPattern();

        return product.display(_manager);
    }
    public JPanel strategy() {
        PatternFactory factory = new StrategyFactory();
        PatternProduct product = factory.createPattern();

        return product.display(_manager);
    }
}
class GUI {
    public static void main(String[] args) {
        new TemplateTest();
    }
}

