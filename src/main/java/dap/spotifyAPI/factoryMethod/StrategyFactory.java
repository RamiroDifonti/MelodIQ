package dap.spotifyAPI.factoryMethod;

public class StrategyFactory implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        System.out.println("Strategy Pattern");

        return new StrategyProduct();
    }
}
