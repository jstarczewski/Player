package com.jstarczewski.src.util;


import com.jstarczewski.src.logic.Element;

import java.util.ArrayList;


public class DataParser {

    public static int size;

    public static ArrayList<Element> parseInputData(String blackSpotDataConfiguration) {
        ArrayList<Element> elements = new ArrayList<Element>();
        String[] points = blackSpotDataConfiguration.split(",");
        for (String point : points) {
            String[] xy = point.split(";");
            String x = xy[0].substring(1, xy[0].length());
            String y = xy[1].substring(0, xy[1].length() - 1);
            elements.add(new Element(Integer.valueOf(x), Integer.valueOf(y)));
        }
        return elements;
    }

    public static Element parseInputDataToElement(String data) {
        return getCords(parseInputData(data));
    }

    public static String parseOutputData(Element element) {
        return "{" +
                (element.getX()) / size +
                ";" +
                (element.getX() - ((element.getX() / size) * size)) +
                "},{" +
                (element.getY()) / size +
                ";" +
                (element.getY() - ((element.getY() / size) * size)) +
                "}";

    }

    private static Element getCords(ArrayList<Element> elements) {
        return new Element(elements.get(0).getX() * size + elements.get(0).getY(), elements.get(1).getX() * size + elements.get(1).getY());
    }

}
