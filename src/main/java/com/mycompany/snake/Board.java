package com.mycompany.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.Timer;

/**
 *
 * @author angprabar
 */
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

    class MyKeyAdapter extends KeyAdapter {

        private boolean paused = false;

        public boolean isPaused() {
            return paused;
        }

        public void setPaused(boolean paused) {
            this.paused = paused;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (snake.getDirection() != Direction.RIGHT) {
                        snake.changeDirection(Direction.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() != Direction.LEFT) {
                        snake.changeDirection(Direction.RIGHT);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (snake.getDirection() != Direction.DOWN) {
                        snake.changeDirection(Direction.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != Direction.UP) {
                        snake.changeDirection(Direction.DOWN);
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    paused = !paused;
                    break;
                default:
                    break;
            }
            repaint();
        }
    }

    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        keyAdapter = new MyKeyAdapter();
        setFocusable(true);
        addKeyListener(keyAdapter);

        snake = new Snake(this);
        timer = new Timer(DELTA_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
            }
        });
        /*
        int specialTime (int) (Math.random() * (MAX_SPECIAL_TIME - MIN_SPECIAL_TIME)) + MIN_SPECIAL_TIME;
        specialTimer = new Timer (specialTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                specialFood = new SpecialFood();
            }
        });*/
        initGame();
    }

    public void initGame() {
        food = new Food(snake, this);
        timer.start();
        scoreboard = new Scoreboard();
    }

    public void tick() {
        if (snake.canMove()) {
            snake.move();
        } else {
            timer.stop();
        }
        repaint();
        if (snake.eats(food)) {
            snake.grow(food.getNodesWhenEat());
            food = new Food(snake, this);
            scoreboard.addPoints(food.getPoints());
        }
        if (keyAdapter.isPaused()) {
            return;
        }
        if (specialFood == null && Math.random() < 0.05) {
            specialFood = new SpecialFood(snake, this);
        }
        if (specialFood != null && specialFood.hasToBeErased()) {
            specialFood = null;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBorderBoard(g);
        if (snake != null) {
            snake.paint(g);
        }
        if (food != null) {
            food.paint(g, squareWidth(), squareHeight());
        }
        if (specialFood != null) {
            specialFood.paint(g, squareWidth(), squareHeight());
        }
        Toolkit.getDefaultToolkit().sync();
        g.drawString("Score: " + scoreboard.getScore(), 10, 20);
    }

    private void paintBorderBoard(Graphics g) {

    }

    private int squareWidth() {
        return getWidth() / NUM_COL;
    }

    private int squareHeight() {
        return getHeight() / NUM_ROW;
    }

    public void drawSquare(Graphics g, int row, int col, NodeType nodeType) {
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = null;
        switch (nodeType) {
            case HEAD:
                color = Color.red;
                break;
            case BODY:
                color = Color.blue;
                break;
            case FOOD:
                color = Color.green;
                break;
            case SPECIAL_FOOD:
                color = Color.yellow;
                break;
        }
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2,
                squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1,
                y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
