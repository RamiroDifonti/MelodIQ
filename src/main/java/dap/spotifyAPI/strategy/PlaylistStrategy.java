package dap.spotifyAPI.strategy;

public class PlaylistStrategy implements Strategy {
    @Override
    public void execute(String string) {
        System.out.println("PlaylistStrategy: " + string);
    }
}
