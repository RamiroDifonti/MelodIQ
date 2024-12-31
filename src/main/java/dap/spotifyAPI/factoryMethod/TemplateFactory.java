package dap.spotifyAPI.factoryMethod;

public class TemplateFactory implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        System.out.println("Template Pattern");

        return new TemplateProduct();
    }
}
