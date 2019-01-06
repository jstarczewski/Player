package com.jstarczewski.logic;

import com.jstarczewski.logic.mcts.Element;

import java.util.ArrayList;

public interface Algorithm {

    ArrayList<Element> getOptimalMoveData(ArrayList<Element> coordinates);

    ArrayList<Element> getStartMoveData();

    void setPlayer(boolean isPlayerEven);

}
