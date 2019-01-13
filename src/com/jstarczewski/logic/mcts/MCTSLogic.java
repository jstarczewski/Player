package com.jstarczewski.logic.mcts;

import com.jstarczewski.logic.Logic;
import com.jstarczewski.logic.mcts.algorithm.MonteCarloTreeSearch;
import com.jstarczewski.logic.mcts.board.Board;
import com.jstarczewski.logic.Element;
import com.jstarczewski.util.DataParser;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MCTSLogic implements Logic {

    private MonteCarloTreeSearch monteCarloTreeSearch;
    private Board board;
    private int player = 1;

    public MCTSLogic(Board board, MonteCarloTreeSearch monteCarloTreeSearch) {
        this.monteCarloTreeSearch = monteCarloTreeSearch;
        this.board = board;
    }

    @Override
    public void initSize(int size) {
        DataParser.size = size;
        board.setSize(size);
    }

    @Override
    public void initBlackSpots(ArrayList<Element> configuration) {
        board.filter(configuration);
    }

    @Override
    public Element getOptimalMoveData(Element element) {

        try {
            board.performMove(player, element);
            if (board.getMoves().size() > 800) {
                board = Entropy.reverseMove(board, element, player);
            } else {
                board = monteCarloTreeSearch.findNextMove(board, player);
            }
        } catch (NoSuchElementException e) {
            board = Entropy.reverseMove(board, element, player);
        }
        return board.getLastMove();
    }

    @Override
    public Element getStartMoveData() {
        Element element = new Element(0, 0);
        try {
            if (board.getMoves().size() > 800) {
                board = Entropy.reverseMove(board, element, player);
            } else {
                board = monteCarloTreeSearch.findNextMove(board, player);
            }
        } catch (NoSuchElementException e) {
            board = Entropy.reverseMove(board, element, player);
        }
        return board.getLastMove();
    }

    @Override
    public boolean isGameEnd() {
        return board.getMoves().isEmpty();
    }

    @Override
    public void setPlayer(int player) {
        board.setLastPlayer(player);
    }
}
