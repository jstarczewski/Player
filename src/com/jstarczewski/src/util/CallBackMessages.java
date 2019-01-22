package com.jstarczewski.src.util;

public class CallBackMessages {


    public static final String dataFormatErrorCallback = "Wrong data fromat at : ";
    public static final String arrayIndexOutOfBoundErrorCallback = "Passed data of coordinates is nor formatted properly at : ";
    public static final String stringOutOfBoundErrorException = "Passed string is not formatted properly at : ";
    public static final String ioErrorCallback = "input/output error occurred : ";
    public static final String nullErrorCallback = "Null pointer occurred : ";
    public static final String illegalArgumentCallback = "Board size must be greater than 2";

    public static final String successCallback = "ok";
    public static final String gameEndCallBack = "Game ended";
    public static final String gameAlreadyStartedCallBack = "Game already started";
    public static final String noSuchMoveAvailableErrorCallback = "Algorithm does not consider this move available ";
    public static final String noMoveErrorCallBack = "Algorithm could not find optimal move, opponent probably won";
}
