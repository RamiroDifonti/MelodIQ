package dap.spotifyAPI.factoryMethod;

public class TemplateFactory implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        return new TemplateProduct();
    }
}
