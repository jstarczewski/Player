package com.jstarczewski.logic.minmax;

import com.jstarczewski.board.Board;

import java.util.ArrayList;
import java.util.List;

class Node {

    private Board board;
    private int score;
    private List<Node> children;

    Node(Board board) {
        this.board = board;
        children = new ArrayList<>();
    }


    public void addChild(Node node) {
        children.add(node);
    }
/*
    public boolean isEvenPlayer() {
        return isEvenPlayer;
    }

    public void setEvenPlayer(boolean evenPlayer) {
        isEvenPlayer = evenPlayer;
    }
*/
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
