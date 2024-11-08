package dap.spotifyAPI.abstractfactory;

import dap.spotifyAPI.products.jazz.JazzProduct;
import dap.spotifyAPI.products.pop.PopProduct;
import dap.spotifyAPI.products.reggaeton.ReggaetonProduct;

public class SportFactory implements PlaylistFactory {
  @Override
  public PopProduct createPop() {
    return new SportPop();
  }

  @Override
  public JazzProduct createJazz() {
    return new SportJazz();
  }

  @Override
  public ReggaetonProduct createReggaeton() {
    return new SportReggaeton();
  }
}