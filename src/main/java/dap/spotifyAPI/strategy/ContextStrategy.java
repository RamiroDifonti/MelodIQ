package dap.spotifyAPI.strategy;

public class ContextStrategy {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(String input) {
        if (strategy != null) {
            strategy.execute(input);
        } else {
            System.out.println("Estrategia no definida");
        }
    }
}
