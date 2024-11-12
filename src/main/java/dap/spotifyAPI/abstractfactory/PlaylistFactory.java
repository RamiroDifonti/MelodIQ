package dap.spotifyAPI.abstractfactory;

import dap.spotifyAPI.products.jazz.JazzProduct;
import dap.spotifyAPI.products.pop.PopProduct;
import dap.spotifyAPI.products.reggaeton.ReggaetonProduct;

/**
 * Interfaz para la creación de productos de playlists de diferentes géneros musicales.
 * Esta interfaz define los métodos para crear productos de tipo Pop, Jazz y Reggaeton.
 * Las implementaciones concretas de esta interfaz, como {@link PartyFactory},
 * se encargan de generar los productos adecuados según el estilo deseado.
 */
public interface PlaylistFactory {

    /**
     * Método para crear un producto de tipo Pop.
     *
     * @return Un objeto de tipo {@link PopProduct} representando una playlist en el género Pop.
     */
    PopProduct createPop();

    /**
     * Método para crear un producto de tipo Jazz.
     *
     * @return Un objeto de tipo {@link JazzProduct} representando una playlist en el género Jazz.
     */
    JazzProduct createJazz();

    /**
     * Método para crear un producto de tipo Reggaeton.
     *
     * @return Un objeto de tipo {@link ReggaetonProduct} representando una playlist en el género Reggaeton.
     */
    ReggaetonProduct createReggaeton();
}
