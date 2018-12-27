package com.jstarczewski.inputconsumer;


/**
 * BaseCallBack that has specific callBacks for certain Actions
 * */
public interface BaseCallBack {

    /**
     * Right now only method notify() is needed but other specific callbacks that extend BaseCallback where implemented
     * so it is easier to understand how code work
     * */

    interface ConfigCallBack extends BaseCallBack {}
    interface BlackSpotsCallBack extends BaseCallBack {}
    interface MoveCallBack extends BaseCallBack {}

    void notify(String callBack);
}
