package dap.spotifyAPI.abstractfactory;

import dap.spotifyAPI.products.jazz.JazzProduct;
import dap.spotifyAPI.products.jazz.PartyJazz;
import dap.spotifyAPI.products.pop.PopProduct;
import dap.spotifyAPI.products.reggaeton.PartyReggaeton;
import dap.spotifyAPI.products.reggaeton.ReggaetonProduct;
import dap.spotifyAPI.products.pop.PartyPop;

public class PartyFactory implements PlaylistFactory {
    @Override
    public PopProduct createPop() {
        return new PartyPop();
    }

    @Override
    public JazzProduct createJazz() {
        return new PartyJazz();
    }

    @Override
    public ReggaetonProduct createReggaeton() {
        return new PartyReggaeton();
    }
}
