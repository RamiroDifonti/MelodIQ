package dap.spotifyAPI.factoryMethod;

public class StrategyFactory implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        System.out.println("Strategy Pattern");

        // crear nombre:


        return new StrategyProduct();
    }
}
