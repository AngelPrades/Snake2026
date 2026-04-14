/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

/**
 *
 * @author angprabar
 */
public class Food extends Node {

    private DrawSquareInterface drawSquareInterface;

    public Food(Snake snake, DrawSquareInterface drawSquareInterdace) {
        super(0, 0);
        this.drawSquareInterface = drawSquareInterface;
        do {
            int row = (int) (Math.random() * Board.NUM_ROW);
            int col = (int) (Math.random() * Board.NUM_COL);
            setRow(row);
            setCol(col);
            while (snake.contains(row, col)) {
                row = (int) (Math.random() * Board.NUM_ROW);
                col = (int) (Math.random() * Board.NUM_COL);
            }
    public void paint(Graphics g, int squareWidth, int squareHeight) {
        Util.drawSquare(g, getRow().getCol(), NodeType.FOOD, squareWidth, squarHeight);
    }

    public int getPoints() {
        return 10;
    }

    public int getNodesWhenEat() {
        return 1;
    }

    public boolean hasToBeErased() {
        return false;
    }
}
