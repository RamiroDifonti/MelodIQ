package dap.spotifyAPI.factoryMethod;

public class FacadeFactory implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        return new FacadeProduct();
    }
}