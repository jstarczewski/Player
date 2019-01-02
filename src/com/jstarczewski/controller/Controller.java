package com.jstarczewski.controller;

import com.jstarczewski.board.Board;
import com.jstarczewski.board.Element;
import com.jstarczewski.logic.Logic;
import com.jstarczewski.util.CallBackMessages;
import com.jstarczewski.util.DataParser;

import java.util.ArrayList;


/**
 * controller class is responsible for 'controlling' what is going on based on input received from GameInputConsumer
 */

public class Controller {

    private Board board;
    private Logic logic;

    private boolean isGameRunning = false;

    public Controller(Board board, Logic logic) {
        this.board = board;
        this.logic = logic;
    }

    public String initBoard(String size) {
        board.setBoardSize(Integer.valueOf(size));
        return CallBackMessages.successCallback;
    }

    public String initBlackSpots(String config) {
        board.setMoveIndex(-1);
        board.fillBoard(DataParser.parseInputData(config));
        board.setMoveIndex(1);
        return CallBackMessages.successCallback;
    }

    private String makeStartMove() {
        logic.setPlayerEven(false);
        return DataParser.parseOutputData(logic.getStartMoveData(board));
    }

    private String makeMove(String moveData) {
        board.fillBoard(DataParser.parseInputData(moveData));
        ArrayList<Element> optimalMoveData = logic.getOptimalMoveData(board);
        if (optimalMoveData.isEmpty()) {
            return CallBackMessages.noMoveErrorCallBack;
        } else {
            board.fillBoard(optimalMoveData);
            return DataParser.parseOutputData(optimalMoveData);
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
