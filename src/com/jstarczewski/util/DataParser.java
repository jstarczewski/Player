package com.jstarczewski.util;

import com.jstarczewski.logic.minmax.board.Element;

import java.util.ArrayList;

public class DataParser {

    public static ArrayList<Element> parseInputData(String blackSpotDataConfiguration) {
        ArrayList<Element> elements = new ArrayList<Element>();
        // {1;2},{2;3}
        String[] points = blackSpotDataConfiguration.split(",");
        for (String point : points) {
            String[] xy = point.split(";");
            String x = xy[0].substring(1, xy[0].length());
            String y = xy[1].substring(0, xy[1].length() - 1);
            elements.add(new Element(Integer.valueOf(x), Integer.valueOf(y)));
        }
        return elements;
    }

    public static String parseOutputData(ArrayList<Element> elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Element element : elements) {
            stringBuilder.append("{");
            stringBuilder.append(element.getX());
            stringBuilder.append(";");
            stringBuilder.append(element.getY());
            stringBuilder.append("}");
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }


}
