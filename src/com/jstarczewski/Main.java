package com.jstarczewski;

import com.jstarczewski.inputconsumer.GameInputConsumer;
import com.jstarczewski.inputconsumer.BaseCallBack;
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
        gameInputConsumer.consumeConfigMessage(new BaseCallBack.ConfigCallBack() {
            @Override
            public void notify(String callBack) {
                System.out.println(callBack);
            }
        });

        /**
         * GameInputConsumer tries to consume BlackSpotConfiguration and returns CallBackMessage depends on the action
         * */
        gameInputConsumer.consumeBlackSpotsConfiguration(new BaseCallBack.BlackSpotsCallBack() {
            @Override
            public void notify(String callBack) {
                System.out.println(callBack);
            }
        });
        /**
         * If everything was set correctly game can be started and messages from 'logic' are sent back via CallBack
         * */
        gameInputConsumer.startGame(new BaseCallBack.MoveCallBack() {
            @Override
            public void notify(String callBack) {
                System.out.println(callBack);
            }
        });
    }
}
