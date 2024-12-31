package dap.spotifyAPI.factoryMethod;

public class MediatorPattern implements PatternFactory {
    @Override
    public PatternProduct createPattern() {
        System.out.println("Mediator Pattern");

        return new MediatorProduct();
    }
}
