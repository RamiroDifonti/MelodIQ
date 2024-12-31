package dap.spotifyAPI.factoryMethod;

public class ObserverPattern implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        System.out.println("Observer Pattern");
    }
}
