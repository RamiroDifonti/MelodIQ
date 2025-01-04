package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;
//import dap.spotifyAPI.mvc.TemplateProduct;

import javax.swing.*;

public class TemplateFactory implements PatternFactory {
    private final MainController controller;

    public TemplateFactory(MainController controller) {
        this.controller = controller;
    }

    @Override
    public JPanel createProduct() {
        return new TemplateProduct(controller);
    }
}
