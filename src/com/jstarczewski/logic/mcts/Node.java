package com.jstarczewski.logic.mcts;

import java.util.*;

class Node {

    State state;
    Node parent;

    HashSet<Node> childArray;

    public Node() {
        this.state = new State();
        childArray = new HashSet<>();
    }

    public Node(State state) {
        this.state = state;
        childArray = new HashSet<>();
    }

    public Node(State state, Node parent, HashSet<Node> childArray) {
        this.state = state;
        this.parent = parent;
        this.childArray = childArray;
    }

    public Node(Node node) {
        this.childArray = new HashSet<>();
        this.state = new State(node.getState());
        if (node.getParent() != null)
            this.parent = node.getParent();
        HashSet<Node> childArray = node.getChildArray();
        for (Node child : childArray) {
            this.childArray.add(new Node(child));
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public HashSet<Node> getChildArray() {
        return childArray;
    }

    public void setChildArray(HashSet<Node> childArray) {
        this.childArray = childArray;
    }

    public Node getRandomChildNode() {
        return this.childArray.iterator().next();
    }

    public Node getChildWithMaxScore() {
        return Collections.max(this.childArray, Comparator.comparing(c -> c.getState().getVisitCount()));
    }


}
