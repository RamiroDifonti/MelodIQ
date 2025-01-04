package dap.spotifyAPI.mvc;

import javax.swing.*;
import java.awt.*;

public class ObserverProduct extends JPanel {
    private final MainController controller;

    public ObserverProduct(MainController controller) {
        this.controller = controller;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Notificaciones de Álbumes");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton notifyButton = new JButton("Notificar Álbumes");
        notifyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        notifyButton.addActionListener(e -> {
            String artistName = JOptionPane.showInputDialog(this, "Introduce el nombre del artista:");
            if (artistName != null && !artistName.trim().isEmpty()) {
                controller.handleObserverNotification(artistName);
            }
        });

        add(Box.createVerticalGlue());
        add(title);
        add(Box.createVerticalStrut(20));
        add(notifyButton);
        add(Box.createVerticalGlue());
    }
}
