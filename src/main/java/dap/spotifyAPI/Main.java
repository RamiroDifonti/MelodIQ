package dap.spotifyAPI;

import dap.spotifyAPI.factoryMethod.*;
import dap.spotifyAPI.mvc.MainController;
import dap.spotifyAPI.proxy.Proxy;
import dap.spotifyAPI.proxy.Spotify;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        String client_id = "c9072e3391954da9b7caf8cdcb0e8d34";
        String client_secret = "acbab08c63964194bf8989dc832b2947";

        Proxy proxy = new Proxy(new Spotify(client_id, client_secret));
        MainController controller = new MainController(proxy);

        PatternFactory factory = new ObserverFactory(controller);
        PatternProduct observerProduct = factory.createProduct();

        factory = new TemplateFactory(controller);
        PatternProduct templateProduct = factory.createProduct();

        factory = new FacadeFactory(controller);
        PatternProduct facadeProduct = factory.createProduct();

        factory = new StrategyFactory(controller);
        PatternProduct strategyProduct = factory.createProduct();

        JFrame frame = new JFrame("MelodIQ");
        frame.setSize(1920, 1080);
        frame.setLayout(new GridLayout(2, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(observerProduct.display());
        frame.add(templateProduct.display());
        frame.add(facadeProduct.display());
        frame.add(strategyProduct.display());

        frame.setVisible(true);
    }
}
