package dap.spotifyAPI.factoryMethod;

public class StrategyPattern implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        System.out.println("Strategy Pattern");
    }
}
