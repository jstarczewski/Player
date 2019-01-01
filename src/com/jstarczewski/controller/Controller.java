package com.jstarczewski.controller;

import com.jstarczewski.board.Board;
import com.jstarczewski.board.Element;
import com.jstarczewski.logic.Logic;
import com.jstarczewski.util.CallBackMessages;
import com.jstarczewski.util.InputDataParser;

import java.util.ArrayList;


/**
 * controller class is responsible for 'controlling' what is going on based on input received from InputConsumer
 */

public class Controller {

    private Board board;
    private int playerIndex = 2;
    private int moveIndex = 0;
    private Logic logic;

    private boolean isGameRunning = false;

    public Controller(Board board, Logic logic) {
        this.board = board;
        this.logic = logic;
        logic.setBoard(board);
    }

    public String setBoardSize(String size) {
        board.setBoardSize(Integer.valueOf(size));
        return CallBackMessages.successCallback;
    }

    public String fillBoard(String config) {
        board.setMoveIndex(-1);
        board.fillBoard(InputDataParser.parseInputData(config));
        return CallBackMessages.successCallback;
    }

    private String makeStartMove() {
        playerIndex--;
        // mock test element
        board.setMoveIndex(++moveIndex);
        board.fillBoard(mockTestElementList());
        return board.toString();
    }

    private String makeMove(String moveData) {

        /*
        board.fillBoard(++moveIndex, InputDataParser.parseInputData(moveData));
        //algorithms response
        board.fillBoard(++moveIndex, mockTestElementList());
        */
        board.setMoveIndex(++moveIndex);
        logic.makeMove(InputDataParser.parseInputData(moveData));

        return board.toString();
    }


    public String response(String input) {
        if (input.toLowerCase().equals("stop")) {
            isGameRunning = false;
            return CallBackMessages.gameEndCallBack;
        } else if (input.toLowerCase().equals("start")) {
            if (!isGameRunning) {
                isGameRunning = true;
                return makeStartMove();
            } else {
                return CallBackMessages.gameAlreadyStartedCallBack;
            }
        } else {
            isGameRunning = true;
            return makeMove(input);
        }
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    private ArrayList<Element> mockTestElementList() {
        Element e = new Element(2, 4);
        Element e2 = new Element(2, 5);
        ArrayList<Element> elements = new ArrayList<Element>();
        elements.add(e);
        elements.add(e2);
        return elements;
    }
}
