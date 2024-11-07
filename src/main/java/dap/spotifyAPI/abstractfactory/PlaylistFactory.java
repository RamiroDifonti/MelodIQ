package dap.spotifyAPI.abstractfactory;

import dap.spotifyAPI.products.jazz.JazzProduct;
import dap.spotifyAPI.products.pop.PopProduct;
import dap.spotifyAPI.products.reggaetton.ReggaettonProduct;


public interface PlaylistFactory {
    PopProduct createPop();
    JazzProduct createJazz();
    ReggaettonProduct createReggaetton();

}
