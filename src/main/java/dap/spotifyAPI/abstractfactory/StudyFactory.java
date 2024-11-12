package dap.spotifyAPI.abstractfactory;

import dap.spotifyAPI.products.jazz.JazzProduct;
import dap.spotifyAPI.products.jazz.StudyJazz;
import dap.spotifyAPI.products.pop.PopProduct;
import dap.spotifyAPI.products.pop.StudyPop;
import dap.spotifyAPI.products.reggaeton.ReggaetonProduct;
import dap.spotifyAPI.products.reggaeton.StudyReggaeton;

/**
 * Implementación concreta de la interfaz {@link PlaylistFactory} para la creación de productos de playlists
 * orientados al estudio.
 * Esta clase implementa los métodos para crear productos de playlists en los géneros Pop, Jazz y Reggaeton,
 * con un enfoque adecuado para el estudio.
 */
public class StudyFactory implements PlaylistFactory {

    /**
     * Método que crea una playlist de Jazz con características adecuadas para el estudio.
     *
     * @return Un objeto de tipo {@link JazzProduct} representando una playlist de Jazz para estudio.
     */
    @Override
    public JazzProduct createJazz() {
        return new StudyJazz();
    }

    /**
     * Método que crea una playlist de Pop con características adecuadas para el estudio.
     *
     * @return Un objeto de tipo {@link PopProduct} representando una playlist de Pop para estudio.
     */
    @Override
    public PopProduct createPop() {
        return new StudyPop();
    }

    /**
     * Método que crea una playlist de Reggaeton con características adecuadas para el estudio.
     *
     * @return Un objeto de tipo {@link ReggaetonProduct} representando una playlist de Reggaeton para estudio.
     */
    @Override
    public ReggaetonProduct createReggaeton() {
        return new StudyReggaeton();
    }
}
