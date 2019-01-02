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
        minMax.setPlayerEven(isPlayerEven);
        return minMax.getStartMoveData(board);
    }

    public void setPlayerEven(boolean isPlayerEven) {
        this.isPlayerEven = isPlayerEven;
    }


}
