package com.jstarczewski.src.logic.mcts.board;

import com.jstarczewski.src.logic.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Board {

    private HashSet<Element> moves;
    private int lastPlayer;
    private Element lastMove = new Element(0, 0);
    public static final int IN_PROGRESS = -1;
    private int size;

    public Board() {
    }

    public Board(int size) {
        this.size = size;
        this.moves = new HashSet<>();
        addPossibleMoves();
    }

    public Board(Board board) {
        this.moves = new HashSet<>(board.getMoves());
        this.lastMove = board.getLastMove();
    }

    private void addPossibleMoves() {

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
            int x = move.getX() * size + move.getY();
            moves.removeIf(element -> (element.getX() == x || (element.getY() == x)));
        });
    }

    public void performMove(int lastPlayer, Element move) {
        this.lastPlayer = lastPlayer;
        this.lastMove = new Element(move.getX(), move.getY());
        moves.removeIf(element -> (element.getX() == move.getX()) || (element.getY() == move.getY() || element.getX() == move.getY()) || (element.getY() == move.getX()));
    }


    public int checkStatus() {
        if (!moves.isEmpty())
            return IN_PROGRESS;
        else {
            return lastPlayer;
        }
    }

    public HashSet<Element> getMoves() {
        return moves;
    }

    public void setMoves(HashSet<Element> moves) {
        this.moves = moves;
    }

    public Element getLastMove() {
        return lastMove;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        this.moves = new HashSet<>();
        addPossibleMoves();
    }

    public void setLastPlayer(int lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return lastPlayer == board.lastPlayer &&
                size == board.size &&
                Objects.equals(moves, board.moves) &&
                Objects.equals(lastMove, board.lastMove);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moves, lastPlayer, lastMove, size);
    }
}
