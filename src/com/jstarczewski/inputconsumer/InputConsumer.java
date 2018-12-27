package com.jstarczewski.inputconsumer;

import com.jstarczewski.Controller;
import com.jstarczewski.inputconsumer.InputConsumerCallbacks.BaseCallBack;
import com.jstarczewski.util.Callbacks;

import java.io.BufferedReader;
import java.io.IOException;

public class InputConsumer {


    private BufferedReader bufferedReader;
    private Controller controller;

    public InputConsumer(BufferedReader bufferedReader, Controller controller) {
        this.bufferedReader = bufferedReader;
        this.controller = controller;
    }

    public void consumeConfigMessage(BaseCallBack.ConfigCallBack configCallBack) {
        try {
            String input = bufferedReader.readLine();
            if (input == null)
                configCallBack.notifyArbiter(Callbacks.nullErrorCallback);
            else {
                controller.setBoardSize(input);
                configCallBack.notifyArbiter(Callbacks.successCallback);
            }
        } catch (IOException e) {
            configCallBack.notifyArbiter(Callbacks.ioErrorCallback + e.getMessage());
        }

    }

    public void consumeBlackSpotsConfiguration(BaseCallBack.BlackSpotsCallBack blackSpotsCallBack) {
        try {
            String input = bufferedReader.readLine();
            if (input == null)
                blackSpotsCallBack.notifyArbiter(Callbacks.nullErrorCallback);
            else
                controller.fillBoard(input);
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
                moveCallBack.notifyArbiter(controller.response(input));
        } catch (IOException e) {
            moveCallBack.notifyArbiter(Callbacks.ioErrorCallback);
        }
    }

    public void startGame(BaseCallBack.MoveCallBack moveCallBack) {
        do
            consumeMove(moveCallBack);
        while (controller.isGameRunning());
        closeReader();
    }

    private void closeReader() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
