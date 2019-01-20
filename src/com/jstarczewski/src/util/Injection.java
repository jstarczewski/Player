package com.jstarczewski.src.util;

import com.jstarczewski.src.controller.GameController;
import com.jstarczewski.src.logic.mcts.MCTSLogic;
import com.jstarczewski.src.logic.mcts.algorithm.MonteCarloTreeSearch;
import com.jstarczewski.src.inputconsumer.GameInputConsumer;
import com.jstarczewski.src.logic.mcts.board.Board;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Injection {

    public static GameInputConsumer provideInputConsumer() {
        return GameInputConsumer.getInstance(new BufferedReader(new InputStreamReader(System.in)),
                new GameController(new MCTSLogic(new Board(), new MonteCarloTreeSearch())));
    }

}
