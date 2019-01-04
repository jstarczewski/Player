package com.jstarczewski.logic;

import com.jstarczewski.logic.minmax.board.Element;

import java.util.ArrayList;

public interface Algorithm {

    ArrayList<Element> getOptimalMoveData(ArrayList<Element> coordinates);

    ArrayList<Element> getStartMoveData();

    void setPlayer(boolean isPlayerEven);

}
