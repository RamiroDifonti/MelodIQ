package dap.spotifyAPI.factoryMethod;

public class ObserverFactory implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        return new ObserverProduct();
    }
}
