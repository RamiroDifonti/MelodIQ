package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.proxy.SpotifyInterface;

public class ObserverFactory implements PatternFactory {
    private final SpotifyInterface spotify;

    public ObserverFactory(SpotifyInterface spotify) {
        this.spotify = spotify;
    }

    @Override
    public PatternProduct createProduct() {
        return new ObserverProduct(spotify);
    }
}
