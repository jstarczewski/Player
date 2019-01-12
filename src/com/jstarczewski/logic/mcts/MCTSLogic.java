package com.jstarczewski.logic.mcts;

import com.jstarczewski.logic.Logic;
import com.jstarczewski.logic.mcts.algorithm.MonteCarloTreeSearch;
import com.jstarczewski.logic.mcts.board.Board;
import com.jstarczewski.logic.Element;
import com.jstarczewski.util.DataParser;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class MCTSLogic implements Logic {

    private MonteCarloTreeSearch monteCarloTreeSearch;
    private Board board;
    private int player = 1;
    private ExecutorService executorService;

    public MCTSLogic(ExecutorService executorService, Board board, MonteCarloTreeSearch monteCarloTreeSearch) {
        this.executorService = executorService;
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

        board.performMove(player, element);
        if (board.getMoves().size() > 800) {
            board = Reverse.reverseMove(board, element, player);
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

    private Future<Board> getOptimalBoard() {
        return executorService.submit(() -> {
            return monteCarloTreeSearch.findNextMove(this.board, this.player);
        });
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
