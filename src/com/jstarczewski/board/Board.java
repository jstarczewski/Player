package com.jstarczewski.board;

import java.util.ArrayList;

public class Board {


    private int[][] board;

    private int size;

    public void setBoardSize(int size) {
        this.size = size;
        board = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = 0;
    }

    public void fillBoardWithBlackSpots(ArrayList<Element> blackSpots) {
        for (Element element : blackSpots)
            board[element.getX()][element.getY()] = -1;
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
}
