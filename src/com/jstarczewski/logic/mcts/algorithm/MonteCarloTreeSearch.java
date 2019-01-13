package com.jstarczewski.logic.mcts.algorithm;

import com.jstarczewski.logic.mcts.board.Board;
import com.jstarczewski.logic.mcts.tree.Node;
import com.jstarczewski.logic.mcts.tree.Tree;

import java.util.HashSet;


public class MonteCarloTreeSearch {


    private int level;
    private static final int WIN_SCORE = 100;
    private int opponent;
    Node rootNode;

    public MonteCarloTreeSearch() {
        this.level = 5;
    }

    public Board findNextMove(Board board, int playerNo) {
        long end = System.currentTimeMillis() + 20;
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
            // Phase 1 - Selection
            Node promisingNode = selectPromisingNode(rootNode);
            // Phase 2 - Expansion
            if (promisingNode.getState().getBoard().checkStatus() == Board.IN_PROGRESS)
                expandNode(promisingNode);

            // Phase 3 - Simulation
            Node nodeToExplore = promisingNode;
            if (promisingNode.getChildHashSet().size() > 0) {
                nodeToExplore = promisingNode.getChildNode();
            }
            int playoutResult = simulatePlayout(nodeToExplore);
            // Phase 4 - Update
            backPropogation(nodeToExplore, playoutResult);
        }

        Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);
        return winnerNode.getState().getBoard();

/*
        while (System.currentTimeMillis() < end) {
            // Phase 1 - Selection
            Node promisingNode = rootNode;
            while (promisingNode.getChildHashSet().size() > 0) {
                promisingNode = UCT.findBestNodeWithUCT(promisingNode);
                if (System.currentTimeMillis() > end)
                    return rootNode.getChildWithMaxScore().getState().getBoard();
            }

            // Phase 2 - Expansion
            if (promisingNode.getState().getBoard().checkStatus() == Board.IN_PROGRESS) {
                HashSet<State> possibleStates = promisingNode.getState().getAllPossibleStates();
                for (State state : possibleStates) {
                    if (System.currentTimeMillis() > end)
                        return rootNode.getChildWithMaxScore().getState().getBoard();
                    Node newNode = new Node(state);
                    newNode.setParent(promisingNode);
                    newNode.getState().setPlayerNo(promisingNode.getState().getOpponent());
                    promisingNode.getChildHashSet().add(newNode);
                    if (System.currentTimeMillis() > end)
                        return rootNode.getChildWithMaxScore().getState().getBoard();
                }
            }


            // Phase 3 - Simulation
            Node nodeToExplore = promisingNode;
            if (promisingNode.getChildHashSet().size() > 0) {
                nodeToExplore = promisingNode.getChildNode();
            }
            int playoutResult = 0;
            Node tempNode = new Node(nodeToExplore);
            State tempState = tempNode.getState();
            int boardStatus = tempState.getBoard().checkStatus();
            if (boardStatus == opponent) {
                tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
                playoutResult = boardStatus;
            } else {
                while (boardStatus == Board.IN_PROGRESS) {
                    tempState.togglePlayer();
                    tempState.play();
                    boardStatus = tempState.getBoard().checkStatus();
                    if (System.currentTimeMillis() > end)
                        return rootNode.getChildWithMaxScore().getState().getBoard();
                }
                playoutResult = boardStatus;
            }
            if (System.currentTimeMillis() > end)
                return rootNode.getChildWithMaxScore().getState().getBoard();
            //int playoutResult = simulatePlayout(nodeToExplore);
            // Phase 4 - Update

            if (System.currentTimeMillis() > end)
                return rootNode.getChildWithMaxScore().getState().getBoard();

            Node tmpNode = nodeToExplore;
            while (tmpNode != null) {
                tmpNode.getState().incrementVisit();
                if (tmpNode.getState().getPlayerNo() == playoutResult) {
                    tmpNode.getState().addScore(WIN_SCORE);
                }
                tmpNode = tmpNode.getParent();
                if (System.currentTimeMillis() > end)
                    return rootNode.getChildWithMaxScore().getState().getBoard();
            }

            //  backPropogation(nodeToExplore, playoutResult);
        }
        Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);
        return winnerNode.getState().getBoard();
        */
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private int getMillisForCurrentLevel() {
        return 2 * (this.level - 1) + 1;
    }

}
