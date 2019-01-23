package com.jstarczewski.tests;

import com.jstarczewski.src.logic.Element;
import com.jstarczewski.src.util.DataParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataParserTest {



    @Test
    void parseInputData() {

        ArrayList<Element> elements =  DataParser.parseInputData("{0;0},{0;1}");
        assertEquals(elements.size(), 2);
        assertEquals(elements.get(0).getX(), 0);
        assertEquals(elements.get(0).getY(), 0);
        assertEquals(elements.get(1).getX(), 0);
        assertEquals(elements.get(1).getY(), 1);

    }

    @Test
    void parseInputDataToElement() {

        // The whole element object breaks one fundamental of solid princ. adn will be refactored

        DataParser.size = 3;
        Element element = DataParser.parseInputDataToElement("{0;0},{0;1}");
        assertEquals(DataParser.size, 3);
        assertEquals(element, new Element(0,1));
    }

    @Test
    void parseOutputData() {
       assertEquals(DataParser.parseOutputData(new Element(1,2)), "{0;0},{0;2}");
    }
}