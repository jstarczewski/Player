package com.jstarczewski.inputconsumer.InputConsumerCallbacks;

public interface BaseCallBack {

    interface ConfigCallBack extends BaseCallBack {}
    interface BlackSpotsCallBack extends BaseCallBack {}
    interface MoveCallBack extends BaseCallBack {}
    void notifyArbiter(String callBack);
}
