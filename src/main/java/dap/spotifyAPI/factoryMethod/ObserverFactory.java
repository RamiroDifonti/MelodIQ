package dap.spotifyAPI.factoryMethod;

public class ObserverFactory implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        System.out.println("Observer Pattern");

        return new ObserverProduct();
    }
}
