package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;

public class TemplateFactory implements PatternFactory {
    private final MainController _controller;

    public TemplateFactory(MainController controller) {
        this._controller = controller;
    }

    @Override
    public PatternProduct createProduct() {
        return new TemplateProduct(_controller);
    }
}