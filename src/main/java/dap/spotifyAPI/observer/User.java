package dap.spotifyAPI.observer;

import javax.swing.*;

public class User implements Observer {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        JOptionPane.showMessageDialog(null, "Notificación para " + name + ": " + message, "Nueva Notificación", JOptionPane.INFORMATION_MESSAGE);
    }
}