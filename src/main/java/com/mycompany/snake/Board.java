package com.mycompany.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.Timer;

public class Board extends javax.swing.JPanel implements DrawSquareInterface {

    public static final int NUM_COL = 20;
    public static final int NUM_ROW = 20;
    public static final int DELTA_TIME = 200;

    private Snake snake;
    private Timer timer;
    private MyKeyAdapter keyAdapter;
    private Food food;
    private Food specialFood;
    private Scoreboard scoreboard;
    private int deltaTime = DELTA_TIME;
    private boolean gameOver = false;

    class MyKeyAdapter extends KeyAdapter {
        private boolean paused = false;

        public boolean isPaused() { return paused; }
        public void setPaused(boolean paused) { this.paused = paused; }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (snake.getDirection() != Direction.RIGHT) snake.changeDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() != Direction.LEFT) snake.changeDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    if (snake.getDirection() != Direction.DOWN) snake.changeDirection(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != Direction.UP) snake.changeDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_SPACE:
                    paused = !paused;
                    break;
            }
            repaint();
        }
    }

    public Board() {
        initComponents();
        keyAdapter = new MyKeyAdapter();
        setFocusable(true);
        addKeyListener(keyAdapter);
        snake = new Snake(this);
        timer = new Timer(deltaTime, (ActionEvent ae) -> tick());
        initGame();
    }

    public void setDeltaTime(int dt) {
        this.deltaTime = dt;
        timer.setDelay(dt);
    }

    public void setPlayerName(String name) {
        if (scoreboard != null) scoreboard.setPlayerName(name);
    }

    public void initGame() {
        gameOver = false;
        snake = new Snake(this);
        food = new Food(snake, this);
        specialFood = null;
        scoreboard = new Scoreboard();
        timer.setDelay(deltaTime);
        timer.start();
        requestFocusInWindow();
    }

    public void tick() {
        if (keyAdapter.isPaused() || gameOver) return;

        if (!snake.canMove() || snake.hitsItself()) {
            timer.stop();
            gameOver = true;
            repaint();
            return;
        }

        snake.move();

        if (snake.hitsItself()) {
            timer.stop();
            gameOver = true;
            repaint();
            return;
        }

        if (snake.eats(food)) {
            scoreboard.addPoints(food.getPoints());
            snake.grow(food.getNodesWhenEat());
            food = new Food(snake, this);
        }

        if (specialFood == null && Math.random() < 0.05) {
            specialFood = new SpecialFood(snake, this);
        }
        if (specialFood != null) {
            if (snake.eats((Food) specialFood)) {
                scoreboard.addPoints(specialFood.getPoints());
                snake.grow(specialFood.getNodesWhenEat());
                specialFood = null;
            } else if (specialFood.hasToBeErased()) {
                specialFood = null;
            }
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        if (snake != null) snake.paint(g);
        if (food != null) food.paint(g, squareWidth(), squareHeight());
        if (specialFood != null) specialFood.paint(g, squareWidth(), squareHeight());
        drawHUD(g);
        if (gameOver) drawGameOver(g);
        if (keyAdapter.isPaused() && !gameOver) drawPaused(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawGrid(Graphics g) {
        g.setColor(new Color(30, 30, 30));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(45, 45, 45));
        for (int i = 0; i <= NUM_COL; i++)
            g.drawLine(i * squareWidth(), 0, i * squareWidth(), getHeight());
        for (int i = 0; i <= NUM_ROW; i++)
            g.drawLine(0, i * squareHeight(), getWidth(), i * squareHeight());
    }

    private void drawHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        String text = scoreboard.getPlayerName() + "  |  Score: " + scoreboard.getScore();
        g.drawString(text, 8, 16);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        String msg = "GAME OVER";
        int w = g.getFontMetrics().stringWidth(msg);
        g.drawString(msg, (getWidth() - w) / 2, getHeight() / 2 - 10);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        String score = "Puntuación: " + scoreboard.getScore();
        int sw = g.getFontMetrics().stringWidth(score);
        g.drawString(score, (getWidth() - sw) / 2, getHeight() / 2 + 20);
    }

    private void drawPaused(Graphics g) {
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String msg = "PAUSA";
        int w = g.getFontMetrics().stringWidth(msg);
        g.drawString(msg, (getWidth() - w) / 2, getHeight() / 2);
    }

    private int squareWidth() { return getWidth() / NUM_COL; }
    private int squareHeight() { return getHeight() / NUM_ROW; }

    @Override
    public void drawSquare(Graphics g, int row, int col, NodeType nodeType) {
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color;
        switch (nodeType) {
            case HEAD:         color = new Color(220, 50, 50); break;
            case BODY:         color = new Color(50, 150, 50); break;
            case FOOD:         color = new Color(50, 200, 50); break;
            case SPECIAL_FOOD: color = new Color(255, 210, 0); break;
            default:           color = Color.WHITE;
        }
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
