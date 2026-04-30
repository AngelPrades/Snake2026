package com.mycompany.snake;

import javax.swing.*;
import java.awt.*;

public class ConfigDialog extends JDialog {
    private JTextField nameField;
    private JComboBox<String> levelCombo;
    private int deltaTime = Board.DELTA_TIME;
    private String playerName = "Player";
    private boolean confirmed = false;

    public ConfigDialog(Frame parent) {
        super(parent, "Configuración", true);
        nameField = new JTextField("Player", 15);
        levelCombo = new JComboBox<>(new String[]{"Fácil", "Normal", "Difícil"});
        levelCombo.setSelectedIndex(1);

        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panel.add(new JLabel("Nombre del jugador:"));
        panel.add(nameField);
        panel.add(new JLabel("Nivel:"));
        panel.add(levelCombo);

        JButton okBtn = new JButton("Aceptar");
        JButton cancelBtn = new JButton("Cancelar");
        okBtn.addActionListener(e -> {
            playerName = nameField.getText().trim().isEmpty() ? "Player" : nameField.getText().trim();
            switch (levelCombo.getSelectedIndex()) {
                case 0: deltaTime = 300; break;
                case 1: deltaTime = 200; break;
                case 2: deltaTime = 100; break;
            }
            confirmed = true;
            dispose();
        });
        cancelBtn.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() { return confirmed; }
    public int getDeltaTime() { return deltaTime; }
    public String getPlayerName() { return playerName; }
}
