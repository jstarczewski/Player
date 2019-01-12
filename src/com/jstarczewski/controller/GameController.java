package com.jstarczewski.controller;

import com.jstarczewski.logic.Logic;
import com.jstarczewski.logic.Element;
import com.jstarczewski.util.CallBackMessages;
import com.jstarczewski.util.DataParser;


/**
 * controller class is responsible for 'controlling' what is going on based on input received from GameInputConsumer
 */

public class GameController {

    private Logic mctsLogic;
    private static final int PLAYER_1 = 1;
    private static final int PLAYER_2 = 1;

    private boolean isGameRunning = false;

    public GameController(Logic mctsLogic) {
        this.mctsLogic = mctsLogic;
    }

    public String initBoard(String size) {
        mctsLogic.initSize(Integer.valueOf(size));
        return CallBackMessages.successCallback;
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

        long time = System.currentTimeMillis();
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
            if (!isGameRunning) {
                isGameRunning = true;
                return makeStartMove();
            } else {
                return CallBackMessages.gameAlreadyStartedCallBack;
            }
        } else {
            isGameRunning = true;
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
