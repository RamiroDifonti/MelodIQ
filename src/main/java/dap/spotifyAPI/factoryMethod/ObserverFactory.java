package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;

public class ObserverFactory implements PatternFactory {
    private final MainController _controller;

    public ObserverFactory(MainController controller) {
        this._controller = controller;
    }

    @Override
    public PatternProduct createProduct() {
        return new ObserverProduct(_controller);
    }
}
