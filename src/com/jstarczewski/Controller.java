package com.jstarczewski;

import com.jstarczewski.Board.Board;
import com.jstarczewski.inputconsumer.InputConsumerCallbacks.BaseCallBack;
import com.jstarczewski.util.Callbacks;

public class Controller {

    private Board board;


    private boolean isGameRunning = false;

    public Controller(Board board) {
        this.board = board;
    }

    public void setBoardSize(String size) {

    }

    public void fillBoard(String config) {

    }

    private String makeMove() {
        return "Move";
    }


    public String response(String input) {
        if (input.toLowerCase().equals("stop")) {
            isGameRunning = false;
            return Callbacks.gameEndCallBack;
        } else if (input.toLowerCase().equals("start")) {
            if (!isGameRunning) {
                isGameRunning = true;
                return makeMove();
            } else {
                return Callbacks.gameAlreadyStartedCallBack;
            }
        } else {
            if (isGameRunning)
                return makeMove();
        }
        return Callbacks.actionErrorCallBack;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }
}
