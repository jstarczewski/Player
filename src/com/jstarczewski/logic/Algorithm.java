package com.jstarczewski.logic;

import com.jstarczewski.board.Board;
import com.jstarczewski.board.Element;

import java.util.ArrayList;

public interface Algorithm {

    ArrayList<Element> getOptimalMoveData(Board board);

    ArrayList<Element> getStartMoveData(Board board);

    void setPlayerEven(boolean isPlayerEven);

}
