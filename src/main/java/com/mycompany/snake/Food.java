package com.mycompany.snake;

import java.awt.Graphics;

public class Food extends Node {
    private DrawSquareInterface drawSquareInterface;

    public Food(Snake snake, DrawSquareInterface drawSquareInterface) {
        super(0, 0);
        this.drawSquareInterface = drawSquareInterface;
        int row, col;
        do {
            row = (int) (Math.random() * Board.NUM_ROW);
            col = (int) (Math.random() * Board.NUM_COL);
        } while (snake.contains(row, col));
        setRow(row);
        setCol(col);
    }

    public void paint(Graphics g, int squareWidth, int squareHeight) {
        drawSquareInterface.drawSquare(g, getRow(), getCol(), NodeType.FOOD);
    }

    public int getPoints() { return 10; }
    public int getNodesWhenEat() { return 1; }
    public boolean hasToBeErased() { return false; }
}
