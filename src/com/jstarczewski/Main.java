package com.jstarczewski;

import com.jstarczewski.Board.Board;
import com.jstarczewski.inputconsumer.InputConsumer;
import com.jstarczewski.inputconsumer.InputConsumerCallbacks.BaseCallBack;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        // write your code here
        InputConsumer inputConsumer = new InputConsumer(new BufferedReader(new InputStreamReader(System.in)), new Controller(new Board()));
        inputConsumer.consumeConfigMessage(new BaseCallBack.ConfigCallBack() {
            @Override
            public void notifyArbiter(String callBack) {
                System.out.println(callBack);
            }
        });
        inputConsumer.consumeBlackSpotsConfiguration(new BaseCallBack.BlackSpotsCallBack() {
            @Override
            public void notifyArbiter(String callBack) {
                System.out.println(callBack);
            }
        });
        inputConsumer.startGame(new BaseCallBack.MoveCallBack() {
            @Override
            public void notifyArbiter(String callBack) {
                System.out.println(callBack);
            }
        });
    }
}
