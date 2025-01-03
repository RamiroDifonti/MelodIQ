package dap.spotifyAPI.factoryMethod;

public class StrategyFactory implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        return new StrategyProduct();
    }
}
