package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;

public class StrategyFactory implements PatternFactory {
    private final MainController controller;

    public StrategyFactory(MainController controller) {
        this.controller = controller;
    }

    @Override
    public PatternProduct createProduct() {
        return new StrategyProduct(controller);
    }
}
