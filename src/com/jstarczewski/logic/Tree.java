package com.jstarczewski.logic;

public class Tree {

    private Node root;

    Tree() {

    }

    Tree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
