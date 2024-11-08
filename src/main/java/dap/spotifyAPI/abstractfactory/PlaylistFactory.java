package dap.spotifyAPI.abstractfactory;

import dap.spotifyAPI.products.jazz.JazzProduct;
import dap.spotifyAPI.products.pop.PopProduct;
import dap.spotifyAPI.products.reggaeton.ReggaetonProduct;


public interface PlaylistFactory {
    PopProduct createPop();
    JazzProduct createJazz();
    ReggaetonProduct createReggaeton();

}
