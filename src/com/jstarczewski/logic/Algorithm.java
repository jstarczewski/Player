package com.jstarczewski.logic;

import com.jstarczewski.logic.mcts.board.Element;

import java.util.ArrayList;

public interface Algorithm {

    Element getOptimalMoveData(ArrayList<Element> coordinates);

    ArrayList<Element> getStartMoveData();

    void setPlayer(int player);

}
