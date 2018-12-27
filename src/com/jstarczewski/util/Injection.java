package com.jstarczewski.util;

import com.jstarczewski.Board.Board;
import com.jstarczewski.Controller.Controller;
import com.jstarczewski.inputconsumer.InputConsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Injection {

    /**
     * Simple data injection class providing everything what is needed to build InputConsumer object
     * */

    public static InputConsumer provideInputConsumer() {
        return InputConsumer.getInstance(new BufferedReader(new InputStreamReader(System.in)), new Controller(new Board()));
    }

}
