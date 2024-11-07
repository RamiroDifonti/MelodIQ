package dap.spotifyAPI.abstractfactory;

import products.jazz.JazzProduct;
import products.pop.PopProduct;
import products.reggaetton.ReggaettonProduct;
import utils.Song;

import java.util.ArrayList;
import java.util.List;

public interface PlaylistFactory {
    List<Song> playlist = new ArrayList<>();
    PopProduct addPop();
    JazzProduct addJazz();
    ReggaettonProduct addReggaetton();
}
