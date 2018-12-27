package com.jstarczewski.inputconsumer;

import com.jstarczewski.inputconsumer.InputConsumerCallbacks.BlocksFilledAtStartupCallback;
import com.jstarczewski.inputconsumer.InputConsumerCallbacks.ConfigConsumerCallback;

import java.io.BufferedReader;
import java.io.IOException;

public class InputConsumer {


    private String config;
    private String blocksFilledAtStartupConfiguration;
    private int boardSize = 0;
    private BufferedReader bufferedReader;


    ConfigConsumerCallback configConsumerCallback;
    BlocksFilledAtStartupCallback blocksFilledAtStartupCallback;

    public InputConsumer(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public void consumeConfigInput(ConfigConsumerCallback configConsumerCallback) {
        setConfigConsumerCallback(configConsumerCallback);
        try {
            this.config = bufferedReader.readLine();
            if (this.config != null)
                consumeConfigMessage(this.config);
            else {
                this.configConsumerCallback.onConfigDataNullError("Data error config is null");
            }
        } catch (IOException e) {
            this.configConsumerCallback.onConfigExceptionError("Error occured while reading config message: " + e.getLocalizedMessage());
        }

        setConfig(config);
        setConfigConsumerCallback(configConsumerCallback);


    }

    public void consumeBlocksFilledAtStartupConfigurations(BlocksFilledAtStartupCallback blocksFilledAtStartupCallback) {
        setBlocksFilledAtStartupCallback(blocksFilledAtStartupCallback);
        try {
            blocksFilledAtStartupConfiguration = bufferedReader.readLine();
        } catch (IOException e) {
            blocksFilledAtStartupCallback.onBlocksFilledAtStartupExceptionError("IO error occured" + e.getMessage());
        }

    }

    private void formatBlocksFilledAtStartupConfiguration(String blocksFilledAtStartupConfiguration) {

    }

    private void consumeConfigMessage(String config) {
        try {
            boardSize = Integer.valueOf(config);
            configConsumerCallback.onConfigConsumed("Size of the board is " + boardSize);
        } catch (NumberFormatException e) {
            configConsumerCallback.onConfigDataFormatError("Error consuming data occured " + e.getMessage());
        }
    }

    private void setConfigConsumerCallback(ConfigConsumerCallback configConsumerCallback) {
        this.configConsumerCallback = configConsumerCallback;
    }

    public void setBlocksFilledAtStartupCallback(BlocksFilledAtStartupCallback blocksFilledAtStartupCallback) {
        this.blocksFilledAtStartupCallback = blocksFilledAtStartupCallback;
    }

    private void setConfig(String config) {
        this.config = config;
    }
}
