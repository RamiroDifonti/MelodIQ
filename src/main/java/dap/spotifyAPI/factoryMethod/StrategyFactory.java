package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;
import dap.spotifyAPI.mvc.StrategyProduct;

import javax.swing.*;

public class StrategyFactory implements PatternFactory {
    private final MainController controller;

    public StrategyFactory(MainController controller) {
        this.controller = controller;
    }

    @Override
    public JPanel createProduct() {
        return new StrategyProduct(controller);
    }
}
