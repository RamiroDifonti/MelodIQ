package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;
import dap.spotifyAPI.mvc.ObserverProduct;

import javax.swing.*;

public class ObserverFactory implements PatternFactory {
    private final MainController controller;

    public ObserverFactory(MainController controller) {
        this.controller = controller;
    }

    @Override
    public JPanel createProduct() {
        return new ObserverProduct(controller);
    }
}
