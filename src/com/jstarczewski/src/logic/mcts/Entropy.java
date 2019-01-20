package com.jstarczewski.src.logic.mcts;

import com.jstarczewski.src.logic.Element;
import com.jstarczewski.src.logic.mcts.board.Board;

public class Entropy {

    public static Board reverseMove(Board board, Element element, int player) {

        int base = board.getSize() * board.getSize() - 1;

        Element e = new Element(base - element.getX(), base - element.getY());
        if (board.getMoves().contains(e)) {
            board.performMove(1, e);
            return board;
        }
        e = new Element(base - element.getY(), base - element.getX());
        if (board.getMoves().contains(e)) {
            board.performMove(player, e);
            return board;
        }
        e = new Element(element.getY(), element.getX());
        if (board.getMoves().contains(e)) {
            board.performMove(player, e);
            return board;
        } else {
            e = board.getMoves().iterator().next();
            board.performMove(player, e);
            return board;
        }
    }

}
