package com.jstarczewski.tests;

import com.jstarczewski.src.controller.GameController;
import com.jstarczewski.src.logic.mcts.MCTSLogic;
import com.jstarczewski.src.logic.mcts.algorithm.MonteCarloTreeSearch;
import com.jstarczewski.src.logic.mcts.board.Board;
import com.jstarczewski.src.util.CallBackMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GameControllerTest {

    private GameController controller;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        controller = new GameController(new MCTSLogic(new Board(), new MonteCarloTreeSearch()));
    }

    @org.junit.jupiter.api.Test
    void initBoard() {
        assertEquals(controller.initBoard("12"), CallBackMessages.successCallback);
        assertEquals(controller.initBoard("-1"), CallBackMessages.illegalArgumentCallback);
    }

    @Test
    void initBoardNumberFormat() {
        assertThrows(NumberFormatException.class, () -> {
            controller.initBoard("not a number");
        });
    }

    @Test
    void initBlackSpotsException() {
        assertThrows(NumberFormatException.class, () -> {
           controller.initBlackSpots("{0;not a number");
        });

    }


    @org.junit.jupiter.api.Test
    void initBlackSpots() {
        controller.initBoard("3");
        assertEquals(controller.initBlackSpots("{0;0},{0;1}"), CallBackMessages.successCallback);
    }

    @Test
    void responseBaseOnInputStart() {
        controller.initBoard("3");
        controller.initBlackSpots("{0;0},{0;1}");
        assertTrue(matchesPattern(controller.responseBasedOnInput("start")));
    }

    private boolean matchesPattern(String input) {
        String regex = "\\{\\d+;\\d+},\\{\\d+;\\d+}";
        return input.matches(regex);
    }

    @Test
    void responseBasedOnInputStop() {
        assertEquals(controller.responseBasedOnInput("stop"), CallBackMessages.gameEndCallBack);
    }

    @org.junit.jupiter.api.Test
    void responseBasedOnInputMove() {
        controller.initBoard("3");
        controller.initBlackSpots("{0;0},{0;1}");
        assertTrue(matchesPattern(controller.responseBasedOnInput("{1;0},{1;1}")));
    }

    @org.junit.jupiter.api.Test
    void isGameRunning() {

    }

    @org.junit.jupiter.api.Test
    void stopGame() {

    }
}