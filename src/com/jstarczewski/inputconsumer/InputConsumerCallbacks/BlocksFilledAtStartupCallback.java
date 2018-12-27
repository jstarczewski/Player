package com.jstarczewski.inputconsumer.InputConsumerCallbacks;

public interface BlocksFilledAtStartupCallback {

    void onBlocksFilledAtStartupConsumed(String callback);

    void onBlocksFilledAtStartupExceptionError(String exceptionErrorCallback);

    void onBlocksFilledAtStartupDataNullErrorCallback(String dataNullCallback);

    void onBlocksFilledAtStartupNumverFormatErrorCallback(String numberFormatCallback);

}
