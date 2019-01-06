package com.jstarczewski.logic.mcts;

import com.jstarczewski.logic.Logic;
import com.jstarczewski.logic.mcts.algorithm.MonteCarloTreeSearch;
import com.jstarczewski.logic.mcts.board.Board;
import com.jstarczewski.logic.mcts.board.Element;
import com.jstarczewski.util.DataParser;

import java.util.ArrayList;

public class MCTSLogic implements Logic {

    private MonteCarloTreeSearch monteCarloTreeSearch;
    private Board board;
    private int player = 1;

    public MCTSLogic(MonteCarloTreeSearch monteCarloTreeSearch) {
        this.monteCarloTreeSearch = monteCarloTreeSearch;

    }

    @Override
    public void initSize(int size) {
        DataParser.size = size;
        board = new Board(size);
    }

    @Override
    public void initBlackSpots(ArrayList<Element> configuration) {
        board.filter(configuration);
    }

    @Override
    public Element getOptimalMoveData(ArrayList<Element> coordinates) {
        board.performMove(player, DataParser.parseInputDataToElement(coordinates));
        board = monteCarloTreeSearch.findNextMove(board, player);
        return board.getLastMove();
    }

    @Override
    public ArrayList<Element> getStartMoveData() {
        return null;
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
