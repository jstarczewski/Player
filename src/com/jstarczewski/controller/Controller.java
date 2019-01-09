package com.jstarczewski.controller;

import com.jstarczewski.logic.Logic;
import com.jstarczewski.logic.Element;
import com.jstarczewski.util.CallBackMessages;
import com.jstarczewski.util.DataParser;


/**
 * controller class is responsible for 'controlling' what is going on based on input received from GameInputConsumer
 */

public class Controller {

    private Logic minMaxLogic;

    private boolean isGameRunning = false;

    public Controller(Logic minMaxLogic) {
        this.minMaxLogic = minMaxLogic;
    }

    public String initBoard(String size) {
        minMaxLogic.initSize(Integer.valueOf(size));
        return CallBackMessages.successCallback;
    }

    public String initBlackSpots(String config) {
        minMaxLogic.initBlackSpots(DataParser.parseInputData(config));
        return CallBackMessages.successCallback;
    }

    private String makeStartMove() {
        return DataParser.parseOutputData(minMaxLogic.getStartMoveData());
    }

    private String makeMove(String moveData) {
        Element optimalMove = minMaxLogic.getOptimalMoveData(DataParser.parseInputData(moveData));
        if (optimalMove == null) {
            isGameRunning = false;
            return CallBackMessages.noMoveErrorCallBack;
        } else {
            if (minMaxLogic.isGameEnd())
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

}
