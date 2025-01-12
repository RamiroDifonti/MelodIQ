package dap.spotifyAPI.factoryMethod;

import dap.spotifyAPI.mvc.MainController;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class ObserverProduct extends JPanel implements PatternProduct {
    private final MainController _controller;

    public ObserverProduct(MainController controller) {
        this._controller = controller;
    }

    @Override
    public Component display() {
        DefaultListModel<String> userListModel = new DefaultListModel<>();
        JTextArea notificationArea = new JTextArea(10, 30);
        setLayout(new BorderLayout());
        setBackground(new Color(20, 140, 90));
        Border grayBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        setBorder(grayBorder);

        // Panel izquierdo para gestionar usuarios
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBackground(new Color(20, 140, 90));

        JLabel userLabel = new JLabel("Usuarios:");
        JList<String> userList = new JList<>(userListModel);
        JButton addUserButton = new JButton("Agregar Usuario");

        addUserButton.addActionListener(e -> {
            String userName = JOptionPane.showInputDialog(this, "Introduce el nombre del usuario:");
            if (userName != null && !userName.trim().isEmpty()) {
                userListModel.addElement(userName);
                notificationArea.append("Usuario agregado: " + userName + "\n");
            }
        });
        userList.setBackground(new Color(140, 220, 180));
        userList.setBorder(grayBorder);

        userPanel.add(userLabel);
        JScrollPane userPane = new JScrollPane(userList);
        userPane.setBorder(BorderFactory.createEmptyBorder());
        userPanel.add(userPane);
        userPanel.add(addUserButton);

        // Panel derecho para gestionar artistas y notificaciones
        JPanel artistPanel = new JPanel();
        artistPanel.setLayout(new BoxLayout(artistPanel, BoxLayout.Y_AXIS));
        artistPanel.setBackground(new Color(20, 140, 90));

        JLabel artistLabel = new JLabel("Artistas:");
        JTextField artistField = new JTextField();
        JButton subscribeButton = new JButton("Suscribirse");
        JButton notifyButton = new JButton("Notificar Nuevos Álbumes");
        JButton addAlbumButton = new JButton("Agregar Álbum por ID");

        subscribeButton.addActionListener(e -> {
            String artistName = artistField.getText().trim();
            String userName = userList.getSelectedValue();
            if(_controller.handleAddArtist(artistName, userName)) {
                notificationArea.append(userName + " ahora está suscrito a " + artistName + "\n");
            }
        });

        notifyButton.addActionListener(e -> {
            String artistName = artistField.getText().trim();
            String msg = _controller.handleObserverNotification(artistName);
            if (msg != null) {
                notificationArea.append("Se ha notificado a los suscriptores de " + msg + "\n");
            }
        });

        addAlbumButton.addActionListener(e -> {
            String artistName = artistField.getText().trim();
            String albumId = JOptionPane.showInputDialog(this, "Introduce el ID del álbum:");
            if(_controller.handleAddAlbum(artistName, albumId)) {
                notificationArea.append("Álbum con ID " + albumId + " agregado" + "\n");
            }
        });
        artistPanel.setBorder(BorderFactory.createEmptyBorder());
        artistField.setBackground(new Color(140, 220, 180));
        artistField.setBorder(grayBorder);

        artistPanel.add(artistLabel);
        artistPanel.add(artistField);
        artistPanel.add(subscribeButton);
        artistPanel.add(notifyButton);
        artistPanel.add(addAlbumButton);

        // Área de notificaciones
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BorderLayout());
        notificationPanel.setBackground(new Color(20, 140, 90));
        notificationArea.setBackground(new Color(140, 220, 180));
        notificationArea.setBorder(grayBorder);

        JLabel notificationLabel = new JLabel("Notificaciones:");
        notificationArea.setEditable(false);
        notificationPanel.add(notificationLabel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(notificationArea);
        notificationPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Añadir los paneles al producto
        add(userPanel, BorderLayout.WEST);
        add(artistPanel, BorderLayout.CENTER);
        add(notificationPanel, BorderLayout.SOUTH);
        return this;
    }
}
