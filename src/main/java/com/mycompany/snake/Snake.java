package com.mycompany.snake;

import java.awt.Graphics;
import java.util.*;

public class Snake {
    private Direction direction;
    private List<Node> nodes;
    private DrawSquareInterface drawSquareInterface;
    private int nodesToGrow = 0;

    public Snake(DrawSquareInterface drawSquareInterface) {
        this.drawSquareInterface = drawSquareInterface;
        nodes = new ArrayList<>();
        direction = Direction.RIGHT;
        int col = Board.NUM_COL / 2;
        int row = Board.NUM_ROW / 2;
        for (int i = 3; i >= 0; i--) {
            nodes.add(new Node(row, col - i));
        }
    }

    public boolean eats(Food food) {
        return food.getRow() == nodes.get(0).getRow() && food.getCol() == nodes.get(0).getCol();
    }

    public void grow(int n) {
        nodesToGrow += n;
    }

    public boolean contains(int row, int col) {
        for (Node n : nodes) {
            if (n.getRow() == row && n.getCol() == col) return true;
        }
        return false;
    }

    public boolean contains(Node node) {
        return contains(node.getRow(), node.getCol());
    }

    public Direction getDirection() { return direction; }

    public void changeDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean hitsItself() {
        Node head = nodes.get(0);
        for (int i = 1; i < nodes.size(); i++) {
            if (nodes.get(i).getRow() == head.getRow() && nodes.get(i).getCol() == head.getCol()) {
                return true;
            }
        }
        return false;
    }

    public void paint(Graphics g) {
        for (int i = 0; i < nodes.size(); i++) {
            drawSquareInterface.drawSquare(g, nodes.get(i).getRow(), nodes.get(i).getCol(),
                    i == 0 ? NodeType.HEAD : NodeType.BODY);
        }
    }

    public boolean canMove() {
        Node head = nodes.get(0);
        switch (direction) {
            case UP:    return head.getRow() - 1 >= 0;
            case DOWN:  return head.getRow() + 1 < Board.NUM_ROW;
            case LEFT:  return head.getCol() - 1 >= 0;
            case RIGHT: return head.getCol() + 1 < Board.NUM_COL;
        }
        return true;
    }

    public void move() {
        Node head = nodes.get(0);
        int row = head.getRow();
        int col = head.getCol();
        Node newHead;
        switch (direction) {
            case UP:    newHead = new Node(row - 1, col); break;
            case DOWN:  newHead = new Node(row + 1, col); break;
            case LEFT:  newHead = new Node(row, col - 1); break;
            case RIGHT: newHead = new Node(row, col + 1); break;
            default:    newHead = new Node(row, col + 1);
        }
        nodes.add(0, newHead);
        if (nodesToGrow == 0) {
            nodes.remove(nodes.size() - 1);
        } else {
            nodesToGrow--;
        }
    }
}
