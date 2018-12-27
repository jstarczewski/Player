package com.jstarczewski.inputconsumer;

import com.jstarczewski.Controller.Controller;
import com.jstarczewski.util.CallBackMessages;

import java.io.BufferedReader;
import java.io.IOException;

public class InputConsumer {

    private BufferedReader bufferedReader;
    private Controller controller;
    private static InputConsumer INSTANCE = null;

    /**
     * Input is a Singleton, because we always want only one object to be working with BufferedReader, so less NullPointerExceptions
     * will happen, because there wont be a situation when two object having two BufferedReaders will access same data.
     * <p>
     * InputConsumer is responsible for consuming data from System.in, passing it to Controller and then sending CallBack message
     * to our Main class
     */

    // Simple Singleton
    public static synchronized InputConsumer getInstance(BufferedReader bufferedReader, Controller controller) {
        if (INSTANCE == null)
            INSTANCE = new InputConsumer(bufferedReader, controller);
        return INSTANCE;
    }


    /**
     * consumeConfigMessage tries to read line from System.in and after that checks whether it is null.
     * If the value is null or exception occurred, appropriate CallBack is send to Main class
     * If everything seems alright board size is set and CallBack telling that everything went alright is send to Main class
     */
    public void consumeConfigMessage(BaseCallBack.ConfigCallBack configCallBack) {
        try {
            String input = bufferedReader.readLine();
            if (input == null)
                configCallBack.notify(CallBackMessages.nullErrorCallback);
            else
                configCallBack.notify(controller.setBoardSize(input));
        } catch (IOException e) {
            configCallBack.notify(CallBackMessages.ioErrorCallback + e.getMessage());
        }

    }

    /**
     * Same as consumeConfigMessage, but fills board with black/dead spots
     */
    public void consumeBlackSpotsConfiguration(BaseCallBack.BlackSpotsCallBack blackSpotsCallBack) {
        try {
            String input = bufferedReader.readLine();
            if (input == null)
                blackSpotsCallBack.notify(CallBackMessages.nullErrorCallback);
            else
                blackSpotsCallBack.notify(controller.fillBoard(input));
        } catch (IOException e) {
            blackSpotsCallBack.notify(CallBackMessages.ioErrorCallback);
        }
    }

    /**
     * This methods sends CallBack with move or Error depending on the input read
     */
    private void consumeMove(BaseCallBack.MoveCallBack moveCallBack) {
        try {
            String input = bufferedReader.readLine();
            if (input == null)
                moveCallBack.notify(CallBackMessages.nullErrorCallback);
            else
                moveCallBack.notify(controller.response(input));
        } catch (IOException e) {
            moveCallBack.notify(CallBackMessages.ioErrorCallback);
        }
    }

    /**
     * Start the game and sends CallBacks with move message, after game is finished (flag GameRunning == false) it closes the
     * reader
     */
    public void startGame(BaseCallBack.MoveCallBack moveCallBack) {
        do
            consumeMove(moveCallBack);
        while (controller.isGameRunning());
        closeReader();
    }

    /**
     * Closes the BufferedReader
     */
    private void closeReader() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private InputConsumer(BufferedReader bufferedReader, Controller controller) {
        this.bufferedReader = bufferedReader;
        this.controller = controller;
    }
}
