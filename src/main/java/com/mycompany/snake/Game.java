package com.mycompany.snake;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

public class Game extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Game.class.getName());
    private Board board;

    public Game() {
        initComponents();
        setupMenu();
        showConfigOnStart();
    }

    private void showConfigOnStart() {
        ConfigDialog config = new ConfigDialog(this);
        config.setVisible(true);
        if (config.isConfirmed()) {
            board.setDeltaTime(config.getDeltaTime());
            board.setPlayerName(config.getPlayerName());
        }
        board.initGame();
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Juego");
        JMenuItem newGame = new JMenuItem("Nueva partida");
        newGame.addActionListener(e -> {
            board.initGame();
            board.requestFocusInWindow();
        });
        JMenuItem config = new JMenuItem("Configuración");
        config.addActionListener(e -> {
            ConfigDialog dlg = new ConfigDialog(this);
            dlg.setVisible(true);
            if (dlg.isConfirmed()) {
                board.setDeltaTime(dlg.getDeltaTime());
                board.setPlayerName(dlg.getPlayerName());
                board.initGame();
            }
            board.requestFocusInWindow();
        });
        JMenuItem exit = new JMenuItem("Salir");
        exit.addActionListener(e -> System.exit(0));
        gameMenu.add(newGame);
        gameMenu.add(config);
        gameMenu.addSeparator();
        gameMenu.add(exit);

        JMenu helpMenu = new JMenu("Ayuda");
        JMenuItem about = new JMenuItem("Acerca de...");
        about.addActionListener(e -> new AboutDialog(this).setVisible(true));
        helpMenu.add(about);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        board = new Board();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Snake 2026");
        setLayout(new BorderLayout());
        add(board, BorderLayout.CENTER);
        setSize(420, 450);
        setLocationRelativeTo(null);
        setResizable(false);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new Game().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
