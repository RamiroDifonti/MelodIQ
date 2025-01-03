package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.proxy.SpotifyInterface;

import javax.swing.*;

public class MediatorProduct implements PatternProduct {
    @Override
    public JPanel display(SpotifyInterface manager) {
        System.out.println("Mediator Pattern");
        return new JPanel();
    }
}
