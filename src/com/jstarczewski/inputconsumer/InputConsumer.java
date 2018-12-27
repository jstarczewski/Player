package com.jstarczewski.inputconsumer;

import com.jstarczewski.Board.Board;
import com.jstarczewski.inputconsumer.InputConsumerCallbacks.BaseCallBack;
import com.jstarczewski.util.Callbacks;

import java.io.BufferedReader;
import java.io.IOException;

public class InputConsumer {


    private String config;
    private BufferedReader bufferedReader;
    private boolean isGameRunning = false;
    private Board board;

    public InputConsumer(BufferedReader bufferedReader, Board board) {
        this.bufferedReader = bufferedReader;
        this.board = board;
    }

    public void consumeConfigMessage(BaseCallBack.ConfigCallBack configCallBack) {
        try {
            config = bufferedReader.readLine();
        } catch (IOException e) {
            configCallBack.notifyArbiter(Callbacks.ioErrorCallback + e.getMessage());
        }
        if (config == null)
            configCallBack.notifyArbiter(Callbacks.nullErrorCallback);
        else {
            board.setHeight(Integer.valueOf(config));
            board.setLength(Integer.valueOf(config));
            configCallBack.notifyArbiter(Callbacks.successCallback);
        }
    }

    public void consumeBlackSpotsConfiguration(BaseCallBack.BlackSpotsCallBack blackSpotsCallBack) {
        try {
            config = bufferedReader.readLine();
            if (config == null)
                blackSpotsCallBack.notifyArbiter(Callbacks.nullErrorCallback);
            else
                blackSpotsCallBack.notifyArbiter(Callbacks.successCallback);
        } catch (IOException e) {
            blackSpotsCallBack.notifyArbiter(Callbacks.ioErrorCallback);
        }
    }

    private void consumeMove(BaseCallBack.MoveCallBack moveCallBack) {
        try {
            String input = bufferedReader.readLine();
            if (input == null)
                moveCallBack.notifyArbiter(Callbacks.nullErrorCallback);
            else
                moveCallBack.notifyArbiter(response(input));
        } catch (IOException e) {
            moveCallBack.notifyArbiter(Callbacks.ioErrorCallback);
        }
    }

    private String response(String input) {
        if (input.toLowerCase().equals("stop")) {
            isGameRunning = false;
            closeReader();
            return Callbacks.gameEndCallBack;
        } else if (input.toLowerCase().equals("start")) {
            if (!isGameRunning) {
                isGameRunning = true;
                return Callbacks.gameStartCallBack;
            } else {
                return Callbacks.gameAlreadyStartedCallBack;
            }
        } else {
            if (isGameRunning)
                return Callbacks.moveCallBack;
        }
        return Callbacks.actionErrorCallBack;
    }

    public void startGame(BaseCallBack.MoveCallBack moveCallBack) {
        do
            consumeMove(moveCallBack);
        while (isGameRunning);
    }

    private void closeReader() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
