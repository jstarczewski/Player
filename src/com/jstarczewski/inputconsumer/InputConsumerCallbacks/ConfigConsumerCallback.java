package com.jstarczewski.inputconsumer.InputConsumerCallbacks;

public interface ConfigConsumerCallback {

    void onConfigConsumed(String callBack);

    void onConfigExceptionError(String exceptionErrorCallBack);

    void onConfigDataNullError(String configDataErrorCallback);

    void onConfigDataFormatError(String configDataFormatErrorCallback);


}
