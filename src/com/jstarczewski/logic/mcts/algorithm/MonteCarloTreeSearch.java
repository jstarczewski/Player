package com.jstarczewski.logic.mcts.algorithm;

import com.jstarczewski.logic.mcts.board.Board;
import com.jstarczewski.logic.mcts.tree.Node;
import com.jstarczewski.logic.mcts.tree.Tree;

import java.util.HashSet;


public class MonteCarloTreeSearch {


    private static final int WIN_SCORE = 100;
    private int opponent;
    private Node rootNode;

    public MonteCarloTreeSearch() {
    }

    public Board findNextMove(Board board, int playerNo) {
        long end = System.currentTimeMillis() + 30;
        opponent = 3 - playerNo;
        Tree tree = new Tree();
        rootNode = tree.getRoot();
        rootNode.getState().setBoard(board);
        rootNode.getState().setPlayerNo(opponent);

        opponent = 3 - playerNo;
        tree = new Tree();
        Node rootNode = tree.getRoot();
        rootNode.getState().setBoard(board);
        rootNode.getState().setPlayerNo(opponent);

        while (System.currentTimeMillis() < end) {
            Node promisingNode = selectPromisingNode(rootNode);
            if (promisingNode.getState().getBoard().checkStatus() == Board.IN_PROGRESS)
                expandNode(promisingNode);
            Node nodeToExplore = promisingNode;
            if (promisingNode.getChildHashSet().size() > 0) {
                nodeToExplore = promisingNode.getChildNode();
            }
            int playoutResult = simulatePlayout(nodeToExplore);
            backPropogation(nodeToExplore, playoutResult);
        }

        Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);
        return winnerNode.getState().getBoard();

    }

    private Node selectPromisingNode(Node rootNode) {
        Node node = rootNode;
        while (node.getChildHashSet().size() > 0) {
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }

    public Board getBackUpBoard() {
        return rootNode.getChildWithMaxScore().getState().getBoard();
    }

    private void expandNode(Node node) {
        HashSet<State> possibleStates = node.getState().getAllPossibleStates();
        for (State state : possibleStates) {
            Node newNode = new Node(state);
            newNode.setParent(node);
            newNode.getState().setPlayerNo(node.getState().getOpponent());
            node.getChildHashSet().add(newNode);
        }
        /*
        possibleStates.forEach(state -> {
        });*/
    }

    private void backPropogation(Node nodeToExplore, int playerNo) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.getState().incrementVisit();
            if (tempNode.getState().getPlayerNo() == playerNo) {
                tempNode.getState().addScore(WIN_SCORE);
            }
            tempNode = tempNode.getParent();
        }
    }

    private int simulatePlayout(Node node) {
        Node tempNode = new Node(node);
        State tempState = tempNode.getState();
        int boardStatus = tempState.getBoard().checkStatus();
        if (boardStatus == opponent) {
            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
            return boardStatus;
        }
        while (boardStatus == Board.IN_PROGRESS) {
            tempState.togglePlayer();
            tempState.play();
            boardStatus = tempState.getBoard().checkStatus();
        }

        return boardStatus;
    }


}
