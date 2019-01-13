package com.jstarczewski.inputconsumer;

public interface BaseCallBack {

    interface ConfigCallBack extends BaseCallBack {
    }

    interface BlackSpotsCallBack extends BaseCallBack {
    }

    interface MoveCallBack extends BaseCallBack {
    }

    void notify(String callBack);
}
