package dap.spotifyAPI.abstractfactory;

import dap.spotifyAPI.products.jazz.JazzProduct;
import dap.spotifyAPI.products.pop.PopProduct;
import dap.spotifyAPI.products.reggaetton.ReggaetonProduct;

public class PartyFactory implements PlaylistFactory {
    @Override
    public PopProduct createPop() {
        return null;
    }

    @Override
    public JazzProduct createJazz() {
        return null;
    }

    @Override
    public ReggaetonProduct createReggaetton() {
        return null;
    }
}
