package com.jstarczewski;

import com.jstarczewski.inputconsumer.GameInputConsumer;
import com.jstarczewski.util.Injection;

public class Main {

    public static void main(String[] args) {

        /**
         * Input consumer object responsible with communicating with Arbiter
         */
        GameInputConsumer gameInputConsumer = Injection.provideInputConsumer();
        /**
         * GameInputConsumer tries to consume ConfigMessage and returns CallBackMessage depends on the action
         * */
        gameInputConsumer.consumeConfigMessage(System.out::println);

        /**
         * GameInputConsumer tries to consume BlackSpotConfiguration and returns CallBackMessage depends on the action
         * */
        gameInputConsumer.consumeBlackSpotsConfiguration(System.out::println);
        /**
         * If everything was set correctly game can be started and messages from 'logic' are sent back via CallBack
         * */
        gameInputConsumer.startGame(System.out::println);
    }
}
