package dap.spotifyAPI.abstractfactory;

import dap.spotifyAPI.products.jazz.JazzProduct;
import dap.spotifyAPI.products.jazz.StudyJazz;
import dap.spotifyAPI.products.pop.PopProduct;
import dap.spotifyAPI.products.pop.StudyPop;
import dap.spotifyAPI.products.reggaeton.ReggaetonProduct;
import dap.spotifyAPI.products.reggaeton.StudyReggaeton;

/**
 * Al igual que pasa en el resto de clases concretas, esta clase implementa la interfaz PlaylistFactory y
 * sobreescribe los métodos de creación de productos de la interfaz para crear una playlist de estudio.
 */
public class StudyFactory implements PlaylistFactory {
    /**
     * Metodo que crea una lista de canciones con las características de Jazz.
     * @return JazzProduct
     */
    @Override
    public JazzProduct createJazz() {
        return new StudyJazz();
    }

    /**
     * Metodo que crea una lista de canciones con las características de Pop.
     * @return PopProduct
     */
    @Override
    public PopProduct createPop() {
        return new StudyPop();
    }

    /**
     * Metodo que crea una lista de canciones con las características de Reggaeton.
     * @return ReggaetonProduct
     */
    @Override
    public ReggaetonProduct createReggaeton() {
        return new StudyReggaeton();
    }
}
