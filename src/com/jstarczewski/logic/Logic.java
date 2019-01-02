package com.jstarczewski.logic;

import com.jstarczewski.board.Board;
import com.jstarczewski.board.Element;

import java.util.ArrayList;

public class Logic {

    private boolean isPlayerEven = true;

    private Algorithm minMax;

    public Logic(Algorithm minMax) {
        this.minMax = minMax;
    }

    public ArrayList<Element> getOptimalMoveData(Board board) {
        return minMax.getOptimalMoveData(board);
    }

    public ArrayList<Element> getStartMoveData(Board board) {
        return minMax.getStartMoveData(board);
    }

    public boolean isPlayerEven() {
        return isPlayerEven;
    }

    public void setPlayerEven(boolean playerEven) {
        isPlayerEven = playerEven;
    }

}
