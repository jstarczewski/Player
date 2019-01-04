package com.jstarczewski.logic;

import com.jstarczewski.logic.minmax.board.Element;

import java.util.ArrayList;

public interface Logic extends Algorithm {

    void initSize(int size);
    void initBlackSpots(ArrayList<Element> configuration);
    ArrayList<Element> getStartMoveData();

}
