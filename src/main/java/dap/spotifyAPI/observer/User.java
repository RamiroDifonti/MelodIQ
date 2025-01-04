package dap.spotifyAPI.observer;

import javax.swing.*;

public class User implements Observer {
    private final String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        JOptionPane.showMessageDialog(null, "Usuario: " + name + "\nNotificación: " + message);
    }

    public String getName() {
        return name;
    }
}
