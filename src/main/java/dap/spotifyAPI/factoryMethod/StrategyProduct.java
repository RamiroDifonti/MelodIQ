package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.proxy.SpotifyInterface;

import javax.swing.*;

public class StrategyProduct implements PatternProduct {
    @Override
    public JPanel display(SpotifyInterface manager) {
        System.out.println("Strategy Pattern");
        return new JPanel();
    }
}
