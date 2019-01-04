package com.jstarczewski.logic.minmax;


import com.jstarczewski.logic.minmax.board.Block;

import java.util.ArrayList;
import java.util.List;

class Node {

    private Block block;
    private int score;
    private List<Node> children;

    Node(Block block) {
        this.block = block;
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
    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
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
