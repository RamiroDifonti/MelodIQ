package dap.spotifyAPI.abstractfactory;

import products.jazz.JazzProduct;
import products.pop.PopProduct;
import products.reggaetton.ReggaettonProduct;

public class StudyFactory implements PlaylistFactory {
    @Override
    public PopProduct addPop() {
        return null;
    }

    @Override
    public JazzProduct addJazz() {
        return null;
    }

    @Override
    public ReggaettonProduct addReggaetton() {
        return null;
    }
}
