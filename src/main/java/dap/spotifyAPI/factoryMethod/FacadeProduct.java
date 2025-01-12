package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;
import dap.spotifyAPI.proxy.SpotifyInterface;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FacadeProduct extends JPanel implements PatternProduct {
    private JPanel _songPanel;
    private JPanel _panel;
    private SpotifyInterface _manager;
    private Component[] _components = new Component[20];
    private final MainController _controller;

    public FacadeProduct(MainController controller) {
        this._controller = controller;
    }

    @Override
    public Component display() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Introduzca un artista para reproducir un video de el");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(new Color(20, 140, 90));
        Border grayBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        setBorder(grayBorder);

        JButton search = new JButton("Buscar artista");
        search.setAlignmentX(Component.CENTER_ALIGNMENT);

        search.addActionListener(e -> {
            String artistName = JOptionPane.showInputDialog(this, "Introduce el nombre del artista:");
            if (artistName != null && !artistName.trim().isEmpty()) {
                _controller.handleReproduceVideo(artistName);
            }
        });

        add(Box.createVerticalGlue());
        add(title);
        add(Box.createVerticalStrut(20));
        add(search);
        add(Box.createVerticalGlue());
        return this;
    }
}
