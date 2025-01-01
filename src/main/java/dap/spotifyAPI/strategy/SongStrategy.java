package dap.spotifyAPI.strategy;

public class SongStrategy implements Strategy {
    @Override
    public void execute(String string) {
        System.out.println("SongStrategy: " + string);
    }
}
