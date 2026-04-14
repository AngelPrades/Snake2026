/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Graphics;
import java.util.*;

/**
 *
 * @author angprabar
 */
public class Snake {
    private Direction direction;
    private List<Node> nodes;
    private DrawSquareInterface drawSquareInterface;
    
    public Snake() {
        nodes = new ArrayList<>();
        direction = Direction.RIGHT;
        int col = Board.NUM_COL / 2;
        int row = Board.NUM_ROW / 2;
        for (int i = 0; i < 4; i++) {
            Node node = new Node (row, col + i);
            nodes.add(node);
        }
    }
    
    public boolean eats(Food food) {
        int row = nodes.getFirst().getRow();
        int col = nodes.getFirst().getCol();
        return (food.getRow() == row && food.getCol() == col);
    }
    
    public void grow(int n) {
        nodesToGrow +=n;
    }
    
    public boolean contains(Node node) {
        for (Node n : nodes) {
            if (node.getRow() == n.getRow() && node.getCol() == n.getCol()) {
                return true;
            }
        }
        return false;
    }
  
    public Direction getDirection() {
        return direction;
    }
    
    public Direction setDirection(Direction direction) {
        this.direction = direction;
    }
    
    public void changeDirection(Direction direction) {
        this.direction = direction;
    }
    
    public void paint(Graphics g) {
        boolean first = true;
        for (Node node : nodes ) {
            drawSquareInterface.drawSquare(g, node.getRow(), node.getCol(), first);
            if (first) {
                first = false;
            }
        }
    }
    
    public boolean canMove() {
        switch (direction) {
            case UP:
                return nodes.getFirst().getRow() - 1 >= 0;
            case DOWN:
                return nodes.getFirst().getRow() + 1 < Board.NUM_ROW;
            case LEFT: 
                return nodes.getFirst().getCol() - 1 >= 0;
            case RIGHT:
                return nodes.getFirst().getCol() + 1 < Board.NUM_COL;
        } 
        return true;
    }
    
    public void move() {
        int row = nodes.getFirst().getRow();
        int col = nodes.getFirst().getCol();
        Node node;
        
        switch (direction) {
            case UP:
                node = new Node(row -1, col);
                break;
            case DOWN:
                node = new Node(row +1, col);
                break;
            case LEFT:
                node = new Node(row, col - 1);
                break;
            case RIGHT:
                node = new Node(row, col + 1);
                break;
        }
        nodes.addFirst(node);
        if (nodesToGrow == 0) {
            nodes.removeLast();
        } else {
            nodesToGrow --;
        }
    }
}
