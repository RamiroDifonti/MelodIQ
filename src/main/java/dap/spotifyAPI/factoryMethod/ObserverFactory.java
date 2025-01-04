package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;
import dap.spotifyAPI.proxy.SpotifyInterface;

import javax.swing.*;

public class ObserverFactory implements PatternFactory {
    private final SpotifyInterface spotify;

    public ObserverFactory(SpotifyInterface spotify) {
        this.spotify = spotify;
    }

    @Override
    public JPanel createProduct() {
        return new ObserverProduct(spotify);
    }
}
