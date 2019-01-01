package com.jstarczewski.board;

public class Block {

    private Element e1;
    private Element e2;

    public Block(Element e1, Element e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public Element getE1() {
        return e1;
    }

    public void setE1(Element e1) {
        this.e1 = e1;
    }

    public Element getE2() {
        return e2;
    }

    public void setE2(Element e2) {
        this.e2 = e2;
    }

    @Override
    public String toString() {
        return "Block{" +
                "e1=" + e1.toString() +
                ", e2=" + e2.toString() +
                '}'+"\n";
    }
}
