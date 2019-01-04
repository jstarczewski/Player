package com.jstarczewski.logic.minmax.board;

import java.util.ArrayList;

public class Block {

    private int x1;
    private int y1;
    private int x2;
    private int y2;


    public Block(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Block(Element e1, Element e2) {
        this.x1 = e1.getX();
        this.y1 = e1.getY();
        this.x2 = e2.getX();
        this.y2 = e2.getY();
    }

    public int[] getBlockCordinates() {
        return new int[]{x1, y1, x2, y2};
    }

    public ArrayList<Element> getBlockCordinatesByElement() {
        ArrayList<Element> elements = new ArrayList<>();
        elements.add(new Element(x1, y1));
        elements.add(new Element(x2, y2));
        return elements;
    }
}
