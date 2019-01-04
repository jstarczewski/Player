package com.jstarczewski.logic.minmax;

import com.jstarczewski.logic.Algorithm;
import com.jstarczewski.logic.Logic;
import com.jstarczewski.logic.minmax.board.Board;
import com.jstarczewski.logic.minmax.board.Element;

import java.util.ArrayList;

public class MinMaxLogic implements Logic {

    private boolean isPlayerEven = true;
    private Board board;
    private Algorithm minMax;

    public MinMaxLogic(Algorithm minMax, Board board) {
        this.board = board;
        this.minMax = minMax;
    }

    @Override
    public ArrayList<Element> getOptimalMoveData(ArrayList<Element> elements) {
        board.fillBoard(elements);
        ArrayList<Element> optimalMoveData = minMax.getOptimalMoveData(elements);
        if (optimalMoveData.isEmpty()) {
            board.fillBoard(optimalMoveData);
        }
        return optimalMoveData;
    }

    @Override
    public ArrayList<Element> getStartMoveData() {
        minMax.setPlayer(isPlayerEven);
        ArrayList<Element> startMoveData = minMax.getStartMoveData();
        board.fillBoard(startMoveData);
        return startMoveData;
    }

    @Override
    public void setPlayer(boolean isPlayerEven) {
        this.isPlayerEven = isPlayerEven;
    }


    @Override
    public void initSize(int size) {
        board.setBoardSize(size);
    }

    @Override
    public void initBlackSpots(ArrayList<Element> configuration) {
        board.setMoveIndex(-1);
        board.fillBoard(configuration);
        board.setMoveIndex(1);
    }
}
