package com.jstarczewski.logic;

import com.jstarczewski.board.Board;
import com.jstarczewski.board.Element;

import java.util.ArrayList;

public class Logic {

    private Board board;
    Tree tree;


    /**
     * First we create a List<Element> with coordinates of possible moves
     * -> we search tree iterating by all possibilities where is empty space
     **/

    private MinMax minMax;

    public Logic(MinMax minMax) {
        this.minMax = minMax;
    }


    public boolean makeMove(ArrayList<Element> elements) {
        board.fillBoard(elements);
        minMax.constructTree(board);
        board.fillBoard(minMax.getWinningCord(board.getMoveIndex()+1));
        if (!minMax.isSpace(board)) {
            System.out.println("Koniec gry");
            return false;
        }
        return true;
    }


    public ArrayList<Element> elementCoordinates() {
        // :(
        return null;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


/*
    public static void testPrintBlocks(ArrayList<Block> blocks) {
        System.out.println(blocks.toString());
    }

/*
    public static ArrayList<Block> testSearchBoard(Board board) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        int ai, aj;
        for (int i = 0; i < board.getSize(); i++) {

            for (int j = 0; j < board.getSize(); j++) {
                ai = i;
                aj = j;
                if (i == 0)
                    ai = board.getSize() - 1;
                if (j == 0)
                    aj = board.getSize() - 1;
                if (board.isEmpty(i, j, ai, j))
                    blocks.add(new Block((new Element(i, j)), (new Element(ai, j))));
                if (board.isEmpty(i, j, i, aj))
                    blocks.add(new Block((new Element(i, j)), (new Element(i, aj))));
            }
        }
        return blocks;

    }
    */
}
