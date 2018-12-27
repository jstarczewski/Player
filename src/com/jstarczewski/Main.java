package com.jstarczewski;

import com.jstarczewski.inputconsumer.InputConsumer;
import com.jstarczewski.inputconsumer.InputConsumerCallbacks.ConfigConsumerCallback;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        // write your code here

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        InputConsumer inputConsumer = new InputConsumer(bufferedReader);
        inputConsumer.consumeConfigInput(new ConfigConsumerCallback() {
            @Override
            public void onConfigConsumed(String callBack) {
                System.out.println(callBack);
            }

            @Override
            public void onConfigExceptionError(String exceptionErrorCallBack) {
                System.out.println(exceptionErrorCallBack);
            }

            @Override
            public void onConfigDataNullError(String configDataErrorCallback) {
                System.out.println(configDataErrorCallback);
            }

            @Override
            public void onConfigDataFormatError(String configDataFormatErrorCallback) {
                System.out.println(configDataFormatErrorCallback);
            }

        });

        inputConsumer.con
    }
}
