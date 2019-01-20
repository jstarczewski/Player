package com.jstarczewski.src.logic;


import java.util.ArrayList;

public interface Logic {

    void initSize(int size);

    void initBlackSpots(ArrayList<Element> configuration);

    Element getStartMoveData();

    boolean isGameEnd();

    Element getOptimalMoveData(Element element);

    void setPlayer(int player);

}
