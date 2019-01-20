package com.jstarczewski.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jstarczewski.src.controller.GameController;
import com.jstarczewski.src.logic.mcts.MCTSLogic;
import com.jstarczewski.src.logic.mcts.algorithm.MonteCarloTreeSearch;
import com.jstarczewski.src.logic.mcts.board.Board;
import com.jstarczewski.src.util.CallBackMessages;

class GameControllerTest {

    private GameController controller;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        controller = new GameController(new MCTSLogic(new Board(), new MonteCarloTreeSearch()));
    }

    @org.junit.jupiter.api.Test
    void initBoard() {
        assertEquals(controller.initBoard("12"), CallBackMessages.successCallback);
    }

    @org.junit.jupiter.api.Test
    void initBlackSpots() {
    }

    @org.junit.jupiter.api.Test
    void responseBasedOnInput() {
    }

    @org.junit.jupiter.api.Test
    void isGameRunning() {
    }

    @org.junit.jupiter.api.Test
    void stopGame() {
    }
}