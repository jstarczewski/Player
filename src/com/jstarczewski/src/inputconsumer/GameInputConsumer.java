package com.jstarczewski.src.inputconsumer;

import com.jstarczewski.src.controller.GameController;
import com.jstarczewski.src.util.CallBackMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;


public class GameInputConsumer {

    private BufferedReader bufferedReader;
    private GameController gameController;
    private static GameInputConsumer INSTANCE = null;

    public static synchronized GameInputConsumer getInstance(BufferedReader bufferedReader, GameController gameController) {
        if (INSTANCE == null)
            INSTANCE = new GameInputConsumer(bufferedReader, gameController);
        return INSTANCE;
    }

    public void consumeConfigMessage(BaseCallBack.ConfigCallBack configCallBack) {
        try {
            String input = bufferedReader.readLine();
            if (input == null)
                configCallBack.notify(CallBackMessages.nullErrorCallback);
            else
                configCallBack.notify(gameController.initBoard(input));
        } catch (NumberFormatException e) {
            configCallBack.notify(CallBackMessages.dataFormatErrorCallback + e.getLocalizedMessage());
            gameController.stopGame();
            closeReader();
        } catch (StringIndexOutOfBoundsException e) {
            configCallBack.notify(CallBackMessages.stringOutOfBoundErrorException + e.getLocalizedMessage());
            gameController.stopGame();
            closeReader();
        } catch (ArrayIndexOutOfBoundsException e) {
            configCallBack.notify(CallBackMessages.arrayIndexOutOfBoundErrorCallback + e.getLocalizedMessage());
            gameController.stopGame();
            closeReader();
        } catch (NoSuchElementException e) {
            configCallBack.notify(CallBackMessages.noSuchMoveAvailableErrorCallback + e.getLocalizedMessage());
            gameController.stopGame();
            closeReader();
        } catch (IOException e) {
            gameController.stopGame();
            configCallBack.notify(CallBackMessages.ioErrorCallback + e.getLocalizedMessage());
        }

    }

    public void consumeBlackSpotsConfiguration(BaseCallBack.BlackSpotsCallBack blackSpotsCallBack) {
        try {
            if (!gameController.isGameRunning())
                closeReader();
            String input = bufferedReader.readLine();
            if (input == null)
                blackSpotsCallBack.notify(CallBackMessages.nullErrorCallback);
            else
                blackSpotsCallBack.notify(gameController.initBlackSpots(input));
        } catch (NumberFormatException e) {
            blackSpotsCallBack.notify(CallBackMessages.dataFormatErrorCallback + e.getLocalizedMessage());
            gameController.stopGame();
            closeReader();
        } catch (StringIndexOutOfBoundsException e) {
            blackSpotsCallBack.notify(CallBackMessages.stringOutOfBoundErrorException + e.getLocalizedMessage());
            gameController.stopGame();
            closeReader();
        } catch (ArrayIndexOutOfBoundsException e) {
            blackSpotsCallBack.notify(CallBackMessages.arrayIndexOutOfBoundErrorCallback + e.getLocalizedMessage());
            gameController.stopGame();
            closeReader();
        } catch (NoSuchElementException e) {
            blackSpotsCallBack.notify(CallBackMessages.noSuchMoveAvailableErrorCallback + e.getLocalizedMessage());
            gameController.stopGame();
            closeReader();
        } catch (IOException e) {
            gameController.stopGame();
            blackSpotsCallBack.notify(CallBackMessages.ioErrorCallback + e.getLocalizedMessage());
        }
    }

    private void consumeMove(BaseCallBack.MoveCallBack moveCallBack) {
        try {
            String input = bufferedReader.readLine();
            if (input == null) {
                moveCallBack.notify(CallBackMessages.nullErrorCallback);
            } else {
                moveCallBack.notify(gameController.responseBasedOnInput(input));
            }
        } catch (NumberFormatException e) {
            moveCallBack.notify(CallBackMessages.dataFormatErrorCallback + e.getLocalizedMessage());
            closeReader();
            gameController.stopGame();
        } catch (IndexOutOfBoundsException e) {
            moveCallBack.notify(CallBackMessages.arrayIndexOutOfBoundErrorCallback + e.getLocalizedMessage());
            closeReader();
            gameController.stopGame();
        } catch (NoSuchElementException e) {
            //  moveCallBack.notify(CallBackMessages.noMoveErrorCallBack);
            closeReader();
            gameController.stopGame();
        } catch (IOException e) {
            moveCallBack.notify(CallBackMessages.ioErrorCallback + e.getLocalizedMessage());
        }
    }

    public void startGame(BaseCallBack.MoveCallBack moveCallBack) {
        do
            consumeMove(moveCallBack);
        while (gameController.isGameRunning());
        closeReader();
    }

    private void closeReader() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GameInputConsumer(BufferedReader bufferedReader, GameController gameController) {
        this.bufferedReader = bufferedReader;
        this.gameController = gameController;
    }
}
