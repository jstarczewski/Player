package com.jstarczewski.controller;

import com.jstarczewski.board.Board;
import com.jstarczewski.util.CallBackMessages;
import com.jstarczewski.util.InputDataParser;


/**
 * controller class is responsible for 'controlling' what is going on based on input received from InputConsumer
 */

public class Controller {

    private Board board;

    private boolean isGameRunning = false;

    public Controller(Board board) {
        this.board = board;
    }

    public String setBoardSize(String size) {
        board.setBoardSize(Integer.valueOf(size));
        return CallBackMessages.successCallback;
    }

    public String fillBoard(String config) {
        board.fillBoardWithBlackSpots(InputDataParser.parseBlackSpotInputData(config));
        return CallBackMessages.successCallback;
    }

    private String makeMove() {
        return board.toString();
    }


    public String response(String input) {
        if (input.toLowerCase().equals("stop")) {
            isGameRunning = false;
            return CallBackMessages.gameEndCallBack;
        } else if (input.toLowerCase().equals("start")) {
            if (!isGameRunning) {
                isGameRunning = true;
                return makeMove();
            } else {
                return CallBackMessages.gameAlreadyStartedCallBack;
            }
        } else {
            if (isGameRunning)
                return makeMove();
        }
        return CallBackMessages.actionErrorCallBack;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }
}
