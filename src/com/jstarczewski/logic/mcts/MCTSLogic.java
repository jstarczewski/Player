package com.jstarczewski.logic.mcts;

import com.jstarczewski.logic.Logic;
import com.jstarczewski.logic.mcts.algorithm.MonteCarloTreeSearch;
import com.jstarczewski.logic.mcts.board.Board;
import com.jstarczewski.logic.Element;
import com.jstarczewski.util.DataParser;

import java.util.ArrayList;

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
    public Element getOptimalMoveData(ArrayList<Element> coordinates) {
        board.performMove(player, DataParser.parseInputDataToElement(coordinates));
        if (board.getMoves().size() > 1200) {
            board = Reverse.reverseMove(board, DataParser.parseInputDataToElement(coordinates), player);
        } else {
            board = monteCarloTreeSearch.findNextMove(board, player);
        }
        return board.getLastMove();
    }

    @Override
    public Element getStartMoveData() {
        if (board.getMoves().size() > 1200) {
            Element e = board.getMoves().iterator().next();
            board.performMove(player, e);
        } else {
            board = monteCarloTreeSearch.findNextMove(board, player);
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
