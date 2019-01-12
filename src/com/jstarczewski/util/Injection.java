package com.jstarczewski.util;

import com.jstarczewski.controller.GameController;
import com.jstarczewski.logic.mcts.MCTSLogic;
import com.jstarczewski.logic.mcts.algorithm.MonteCarloTreeSearch;
import com.jstarczewski.inputconsumer.GameInputConsumer;
import com.jstarczewski.logic.mcts.board.Board;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Injection {

    /**
     * Simple data injection class providing everything what is needed to build GameInputConsumer object
     */

    public static GameInputConsumer provideInputConsumer() {
        return GameInputConsumer.getInstance(new BufferedReader(new InputStreamReader(System.in)),
                new GameController(new MCTSLogic(new Board(), new MonteCarloTreeSearch())));
    }

}
