package com.jstarczewski.board;

import java.util.ArrayList;

public class Board {


    private int[][] board;

    private int moveIndex;

    private int size;

    public void setBoardSize(int size) {
        this.size = size;
        board = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = 0;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                stringBuilder.append((board[i][j])).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void fillField(int i, int j, int value) {
        board[i][j] = value;
    }

    public boolean fillBoard(ArrayList<Element> coordinates) {
        for (Element element : coordinates) {
            board[element.getX()][element.getY()] = moveIndex;
        }
        moveIndex++;
        return true;
    }

    public boolean isEmpty(int i, int j, int k, int l) {
        return board[i][j] == 0 && board[k][l] == 0;
    }

    public int getFieldValue(int i, int j) {
        return board[i][j];
    }

    public void setFieldValue(int i, int j, int value) {
        board[i][j] = value;
    }

    public int getMoveIndex() {
        return moveIndex;
    }

    public void setMoveIndex(int moveIndex) {
        this.moveIndex = moveIndex;
    }

    public void copy(Board board) {
        if (board.size == size) {
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    this.board[i][j] = board.getFieldValue(i, j);
        }
    }

    public ArrayList<Element> find(int moveIndex) {
        ArrayList<Element> elements = new ArrayList<>();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j] == moveIndex)
                    elements.add(new Element(i, j));
        return elements;
    }
}
