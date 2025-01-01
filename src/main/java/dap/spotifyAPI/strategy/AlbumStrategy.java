package dap.spotifyAPI.strategy;

public class AlbumStrategy implements Strategy {
    @Override
    public void execute(String string) {
        System.out.println("AlbumStrategy: " + string);
    }
}
