package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;

public class FacadeFactory implements PatternFactory {
    private final MainController _controller;

    public FacadeFactory(MainController controller) {
        this._controller = controller;
    }

    @Override
    public PatternProduct createProduct() {
        return new FacadeProduct(_controller);
    }
}