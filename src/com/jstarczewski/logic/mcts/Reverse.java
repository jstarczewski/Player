package com.jstarczewski.logic.mcts;

import com.jstarczewski.logic.Element;
import com.jstarczewski.logic.mcts.board.Board;

public class Reverse {

    public static Board reverseMove(Board board, Element element, int player) {

        Element e = new Element(board.getSize() - 1 - element.getX(), board.getSize() - 1 - element.getY());
        if (board.getMoves().contains(e)) {
            board.performMove(1, e);
            return board;
        }
        e = new Element(board.getSize() - 1 - element.getX(), element.getY());
        if (board.getMoves().contains(e)) {
            board.performMove(1, e);
            return board;
        }
        e = new Element(element.getX(), board.getSize() - 1 - element.getY());
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
