package com.jstarczewski.logic.mcts;

import com.jstarczewski.logic.Logic;
import com.jstarczewski.util.DataParser;

import java.util.ArrayList;

public class MCTSLogic implements Logic {

    private MonteCarloTreeSearch monteCarloTreeSearch;
    Board board;
    int PlayerN = 1;

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
    public ArrayList<Element> getOptimalMoveData(ArrayList<Element> coordinates) {
        board.performMove(PlayerN, DataParser.parseInpuyDataToElement(coordinates));
        board = monteCarloTreeSearch.findNextMove(board, PlayerN);
        Element bMove = board.getLastMove();
        ArrayList<Element> moves = new ArrayList<>();
        moves.add(bMove);
        return moves;
    }

    @Override
    public ArrayList<Element> getStartMoveData() {
        return null;
    }

    @Override
    public void setPlayer(boolean isPlayerEven) {

    }
}
