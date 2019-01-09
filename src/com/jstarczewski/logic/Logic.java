package com.jstarczewski.logic;


import java.util.ArrayList;

public interface Logic {

    void initSize(int size);

    void initBlackSpots(ArrayList<Element> configuration);

    Element getStartMoveData();

    boolean isGameEnd();

    Element getOptimalMoveData(ArrayList<Element> coordinates);

    void setPlayer(int player);
}
