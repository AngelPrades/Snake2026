package com.mycompany.snake;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {

    public AboutDialog(Frame parent) {
        super(parent, "Acerca de Snake", true);
        JPanel panel = new JPanel(new GridLayout(5, 1, 4, 4));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        panel.add(new JLabel("Snake Angel", SwingConstants.CENTER));
        panel.add(new JLabel("Versión 1.0", SwingConstants.CENTER));
        panel.add(new JLabel("Juego de la serpiente.", SwingConstants.CENTER));
        panel.add(new JLabel("Creado por Angel.", SwingConstants.CENTER));
        JButton closeBtn = new JButton("Cerrar");
        closeBtn.addActionListener(e -> dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.add(closeBtn);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
    }
}
