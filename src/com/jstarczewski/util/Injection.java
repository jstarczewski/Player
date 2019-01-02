package com.jstarczewski.util;

import com.jstarczewski.board.Board;
import com.jstarczewski.controller.Controller;
import com.jstarczewski.inputconsumer.InputConsumer;
import com.jstarczewski.logic.Logic;
import com.jstarczewski.logic.minmax.MinMax;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Injection {

    /**
     * Simple data injection class providing everything what is needed to build InputConsumer object
     */

    public static InputConsumer provideInputConsumer() {
        return InputConsumer.getInstance(new BufferedReader(new InputStreamReader(System.in)), new Controller(new Board(), new Logic(new MinMax())));
    }

}
