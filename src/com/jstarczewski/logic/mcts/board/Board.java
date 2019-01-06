package com.jstarczewski.logic.mcts.board;

import java.util.ArrayList;
import java.util.HashSet;

public class Board {
    private HashSet<Element> moves;

    private int lastPlayer;
    private Element lastMove = new Element(0, 0);

    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    public static final int IN_PROGRESS = -1;
    private int size;

    public Board() {
        this.moves = new HashSet<>();
        addPossibleMoves();
    }

    public Board(int size) {
        this.size = size;
        this.moves = new HashSet<>();
        addPossibleMoves();
    }


    public Board(HashSet<Element> moves) {
        this.moves = moves;
    }

    public Board(HashSet<Element> moves, int lastPlayer) {
        this.moves = new HashSet<>(moves);
        this.lastPlayer = lastPlayer;
    }

    public Board(Board board) {
        this.moves = new HashSet<>(board.getMoves());
    }

    public HashSet<Element> getMoves() {
        return moves;
    }

    public void setMoves(HashSet<Element> moves) {
        this.moves = moves;
    }

    public void addPossibleMoves() {

        int ai, aj, head;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                head = i * size + j;
                ai = head - 1 - (i * size) < 0 ? i * size + j - 1 + size : i * size + j - 1;
                aj = head - size < 0 ? i * size + j - size + (size * size) : i * size + j - size;
                moves.add(new Element(head, ai));
                moves.add(new Element(head, aj));

            }

        }
    }

    public void filter(ArrayList<Element> elements) {
        elements.forEach(move -> {
            moves.removeIf(element -> (element.getX() == move.getX()) || (element.getY() == move.getY()));
            moves.removeIf(element -> (element.getX() == move.getY()) || (element.getY() == move.getX()));
        });
    }

    public void performMove(int lastPlayer, Element move) {
        this.lastPlayer = lastPlayer;
        this.lastMove = move;
        moves.removeIf(element -> (element.getX() == move.getX()) || (element.getY() == move.getY()));
        moves.removeIf(element -> (element.getX() == move.getY()) || (element.getY() == move.getX()));
    }


    public int checkStatus() {
        if (!moves.isEmpty())
            return IN_PROGRESS;
        else {
            return lastPlayer;
        }
    }

    public void printStatus() {
        if (moves.isEmpty())
            System.out.println(lastPlayer + " Won the game");
    }

    public void printBoard() {
        System.out.println(moves.toString());
    }

    public Element getLastMove() {
        return lastMove;
    }

    public void setLastPlayer(int lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

}
