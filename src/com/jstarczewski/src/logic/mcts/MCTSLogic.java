package com.jstarczewski.src.logic.mcts;

import com.jstarczewski.src.logic.Logic;
import com.jstarczewski.src.logic.mcts.algorithm.MonteCarloTreeSearch;
import com.jstarczewski.src.logic.mcts.board.Board;
import com.jstarczewski.src.logic.Element;
import com.jstarczewski.src.util.DataParser;

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
            if (board.getMoves().size() > 600) {
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
            if (board.getMoves().size() > 600) {
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
