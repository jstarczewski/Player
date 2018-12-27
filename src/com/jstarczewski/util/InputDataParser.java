package com.jstarczewski.util;

import com.jstarczewski.board.Element;

import java.util.ArrayList;

public class InputDataParser {

    public static ArrayList<Element> parseBlackSpotInputData(String blackSpotDataConfiguration) {
        ArrayList<Element> blackSpots = new ArrayList<Element>();

        String[] points = blackSpotDataConfiguration.split(",");
        for (String point : points) {
            String[] xy = point.split(";");
            String x = xy[0].substring(1, xy[0].length());
            String y = xy[1].substring(0, xy[1].length() - 1);
            blackSpots.add(new Element(Integer.valueOf(x), Integer.valueOf(y)));
        }
        return blackSpots;
    }

}
