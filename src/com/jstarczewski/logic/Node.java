package com.jstarczewski.logic;

import com.jstarczewski.board.Block;
import com.jstarczewski.board.Board;

import java.util.List;

class Node {

    private Block block;
    private boolean isEvenPlayer;
    private Board board;
    private List<Node> children;



    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public boolean isEvenPlayer() {
        return isEvenPlayer;
    }

    public void setEvenPlayer(boolean evenPlayer) {
        isEvenPlayer = evenPlayer;
    }

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
}
