package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.proxy.SpotifyInterface;

import javax.swing.*;

public class TemplateFactory implements PatternFactory {
    private final SpotifyInterface spotify;

    public TemplateFactory(SpotifyInterface spotify) {
        this.spotify = spotify;
    }

    @Override
    public JPanel createProduct() {
        return new TemplateProduct(spotify);
    }
}
