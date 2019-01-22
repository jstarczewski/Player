package com.jstarczewski.src.controller;

import com.jstarczewski.src.logic.Logic;
import com.jstarczewski.src.logic.Element;
import com.jstarczewski.src.util.CallBackMessages;
import com.jstarczewski.src.util.DataParser;


public class GameController {

    private Logic mctsLogic;
    private static final int PLAYER_1 = 1;
    private static final int PLAYER_2 = 1;

    private boolean isGameRunning = false;

    public GameController(Logic mctsLogic) {
        this.mctsLogic = mctsLogic;
    }

    public String initBoard(String size) {
        int boardSize = Integer.valueOf(size);
        if (boardSize > 2) {
            mctsLogic.initSize(boardSize);
            isGameRunning = true;
            return CallBackMessages.successCallback;
        } else {
            isGameRunning = false;
            return CallBackMessages.illegalArgumentCallback;
        }
    }

    public String initBlackSpots(String config) {
        mctsLogic.initBlackSpots(DataParser.parseInputData(config));
        return CallBackMessages.successCallback;
    }

    private String makeStartMove() {
        mctsLogic.setPlayer(PLAYER_1);
        return DataParser.parseOutputData(mctsLogic.getStartMoveData());
    }

    private String makeMove(String moveData) {
        mctsLogic.setPlayer(PLAYER_2);
        Element optimalMove = mctsLogic.getOptimalMoveData(DataParser.parseInputDataToElement(moveData));
        if (optimalMove == null) {
            isGameRunning = false;
            return CallBackMessages.noMoveErrorCallBack;
        } else {
            if (mctsLogic.isGameEnd())
                isGameRunning = false;
            return DataParser.parseOutputData(optimalMove);
        }
    }


    public String responseBasedOnInput(String input) {
        if (input.toLowerCase().equals("stop")) {
            isGameRunning = false;
            return CallBackMessages.gameEndCallBack;
        } else if (input.toLowerCase().equals("start")) {
            return makeStartMove();
        } else {
            return makeMove(input);
        }
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void stopGame() {
        isGameRunning = false;
    }

}
