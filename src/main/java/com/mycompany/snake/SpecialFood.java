package com.mycompany.snake;

import java.awt.Graphics;

public class SpecialFood extends Food {
    private static final int VISIBLE_TICKS = 15;
    private int ticksRemaining = VISIBLE_TICKS;
    private DrawSquareInterface dsi;

    public SpecialFood(Snake snake, DrawSquareInterface drawSquareInterface) {
        super(snake, drawSquareInterface);
        this.dsi = drawSquareInterface;
    }

    @Override
    public void paint(Graphics g, int squareWidth, int squareHeight) {
        ticksRemaining--;
        dsi.drawSquare(g, getRow(), getCol(), NodeType.SPECIAL_FOOD);
    }

    @Override
    public int getPoints() { return 30; }

    @Override
    public int getNodesWhenEat() { return 3; }

    @Override
    public boolean hasToBeErased() { return ticksRemaining <= 0; }
}
