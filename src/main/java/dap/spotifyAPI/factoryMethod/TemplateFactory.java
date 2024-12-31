package dap.spotifyAPI.factoryMethod;

public class TemplatePattern implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        System.out.println("Template Pattern");
    }
}
