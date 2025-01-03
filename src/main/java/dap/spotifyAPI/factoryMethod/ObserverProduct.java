package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.proxy.SpotifyInterface;

import javax.swing.*;

public class ObserverProduct implements PatternProduct {
    @Override
    public JPanel display(SpotifyInterface manager) {
        System.out.println("Observer Pattern");
        return new JPanel();
    }
}
