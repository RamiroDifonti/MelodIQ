package dap.spotifyAPI.abstractfactory;

import dap.spotifyAPI.products.jazz.JazzProduct;
import dap.spotifyAPI.products.jazz.SportJazz;
import dap.spotifyAPI.products.pop.PopProduct;
import dap.spotifyAPI.products.pop.SportPop;
import dap.spotifyAPI.products.reggaeton.ReggaetonProduct;
import dap.spotifyAPI.products.reggaeton.SportReggaeton;

/**
 * Implementación concreta de la interfaz {@link PlaylistFactory} para la creación de productos de playlists
 * orientados a un estilo más activo y deportivo.
 * Esta clase implementa los métodos para crear productos de playlists en los géneros Pop, Jazz y Reggaeton,
 * con un enfoque deportivo.
 */
public class SportFactory implements PlaylistFactory {

  /**
   * Método para crear un producto de tipo Pop con enfoque deportivo.
   *
   * @return Un objeto de tipo {@link PopProduct} representando una playlist de Pop para deportes.
   */
  @Override
  public PopProduct createPop() {
    return new SportPop();
  }

  /**
   * Método para crear un producto de tipo Jazz con enfoque deportivo.
   *
   * @return Un objeto de tipo {@link JazzProduct} representando una playlist de Jazz para deportes.
   */
  @Override
  public JazzProduct createJazz() {
    return new SportJazz();
  }

  /**
   * Método para crear un producto de tipo Reggaeton con enfoque deportivo.
   *
   * @return Un objeto de tipo {@link ReggaetonProduct} representando una playlist de Reggaeton para deportes.
   */
  @Override
  public ReggaetonProduct createReggaeton() {
    return new SportReggaeton();
  }
}
