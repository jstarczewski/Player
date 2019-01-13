package com.jstarczewski;

import com.jstarczewski.inputconsumer.GameInputConsumer;
import com.jstarczewski.util.Injection;

public class Main {

    public static void main(String[] args) {
        GameInputConsumer gameInputConsumer = Injection.provideInputConsumer();
        gameInputConsumer.consumeConfigMessage(System.out::println);
        gameInputConsumer.consumeBlackSpotsConfiguration(System.out::println);
        gameInputConsumer.startGame(System.out::println);
    }
}
