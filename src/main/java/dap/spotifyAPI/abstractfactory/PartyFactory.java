package dap.spotifyAPI.abstractfactory;

import dap.spotifyAPI.products.jazz.JazzProduct;
import dap.spotifyAPI.products.jazz.PartyJazz;
import dap.spotifyAPI.products.pop.PopProduct;
import dap.spotifyAPI.products.reggaeton.PartyReggaeton;
import dap.spotifyAPI.products.reggaeton.ReggaetonProduct;
import dap.spotifyAPI.products.pop.PartyPop;

/**
 * Implementación concreta de la interfaz {@link PlaylistFactory} para crear productos de tipo
 * Pop, Jazz y Reggaeton para una playlist de fiesta.
 * Esta clase implementa los métodos de la interfaz para crear productos de diferentes géneros musicales
 * de estilo de fiesta.
 */
public class PartyFactory implements PlaylistFactory {

    /**
     * Método que crea un producto de tipo Pop de estilo de fiesta.
     *
     * @return Un objeto de tipo {@link PopProduct} representando una playlist de fiesta en el género Pop.
     */
    @Override
    public PopProduct createPop() {
        return new PartyPop(); // Retorna un nuevo producto de tipo Pop con estilo de fiesta.
    }

    /**
     * Método que crea un producto de tipo Jazz de estilo de fiesta.
     *
     * @return Un objeto de tipo {@link JazzProduct} representando una playlist de fiesta en el género Jazz.
     */
    @Override
    public JazzProduct createJazz() {
        return new PartyJazz(); // Retorna un nuevo producto de tipo Jazz con estilo de fiesta.
    }

    /**
     * Método que crea un producto de tipo Reggaeton de estilo de fiesta.
     *
     * @return Un objeto de tipo {@link ReggaetonProduct} representando una playlist de fiesta en el género Reggaeton.
     */
    @Override
    public ReggaetonProduct createReggaeton() {
        return new PartyReggaeton(); // Retorna un nuevo producto de tipo Reggaeton con estilo de fiesta.
    }
}

