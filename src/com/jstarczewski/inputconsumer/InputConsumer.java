package com.jstarczewski.inputconsumer;

import com.jstarczewski.inputconsumer.InputConsumerCallbacks.BaseCallBack;
import com.jstarczewski.inputconsumer.InputConsumerCallbacks.BlocksFilledAtStartupCallback;
import com.jstarczewski.inputconsumer.InputConsumerCallbacks.ConfigConsumerCallback;
import com.jstarczewski.util.Callbacks;

import java.io.BufferedReader;
import java.io.IOException;

public class InputConsumer {


    private String config;
    private BufferedReader bufferedReader;
    private boolean isGameRunning = true;


    private BaseCallBack baseCallBack;


    ConfigConsumerCallback configConsumerCallback;
    BlocksFilledAtStartupCallback blocksFilledAtStartupCallback;

    public InputConsumer(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public void consumeConfigMessage(BaseCallBack.ConfigCallBack configCallBack) {
        setBaseCallBack(configCallBack);
        try {
            config = bufferedReader.readLine();
        } catch (IOException e) {
            configCallBack.onCall(Callbacks.ioErrorCallback + e.getMessage());
        }
        if (config == null)
            configCallBack.onCall(Callbacks.nullErrorCallback);
        else
            configCallBack.onCall(Callbacks.successCallback);
    }

    public void consumeBlackSpotsConfiguration(BaseCallBack.BlackSpotsCallBack blackSpotsCallBack) {
        setBaseCallBack(blackSpotsCallBack);
        try {
            config = bufferedReader.readLine();
        } catch (IOException e) {
            blackSpotsCallBack.onCall(Callbacks.ioErrorCallback);
        }
        if (config == null)
            blackSpotsCallBack.onCall(Callbacks.nullErrorCallback);
        else
            blackSpotsCallBack.onCall(Callbacks.successCallback);
    }

    private void consumeMove(BaseCallBack.MoveCallBack moveCallBack) {
        try {
            config = bufferedReader.readLine();
        } catch (IOException e) {
            moveCallBack.onCall(Callbacks.ioErrorCallback);
        }
        if (config == null)
            moveCallBack.onCall(Callbacks.nullErrorCallback);
        else
            response();
    }

    private void response() {
        if (config.equals("stop")) {
            isGameRunning = false;
            baseCallBack.onCall(Callbacks.gameEndCallBack);
        } else if (config.equals("start")) {
            // wait
            if (!isGameRunning) {
                isGameRunning = true;
                baseCallBack.onCall(Callbacks.gameStartCallBack);
            } else {
                baseCallBack.onCall(Callbacks.gameAlreadyStartedCallBack);
            }
        } else {
            // makeMove
            if (isGameRunning)
                baseCallBack.onCall(Callbacks.moveCallBack);
        }
    }

    public void startGame(BaseCallBack.MoveCallBack moveCallBack) {
        setBaseCallBack(moveCallBack);
        while (isGameRunning) {
            consumeMove(moveCallBack);
        }
    }

    private void makeMove() {
        baseCallBack.onCall(Callbacks.successCallback);
    }

    private void makeMove(String block) {
    }

    public void setBaseCallBack(BaseCallBack baseCallBack) {
        this.baseCallBack = baseCallBack;
    }

    public void closeReader() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
