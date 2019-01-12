package com.jstarczewski.logic.mcts.algorithm;

import com.jstarczewski.logic.mcts.board.Board;
import com.jstarczewski.logic.mcts.tree.Node;
import com.jstarczewski.logic.mcts.tree.Tree;

import java.util.HashSet;


public class MonteCarloTreeSearch {


    private int level;
    private static final int WIN_SCORE = 100;
    private int opponent;
    private long start = System.currentTimeMillis();
    private Board bestBoard;
    Node rootNode;

    public MonteCarloTreeSearch() {
        this.level = 5;
    }

    public Board findNextMove(Board board, int playerNo) {
        long end = start + 10 * getMillisForCurrentLevel();

        opponent = 3 - playerNo;
        Tree tree = new Tree();
        rootNode = tree.getRoot();
        rootNode.getState().setBoard(board);
        rootNode.getState().setPlayerNo(opponent);

        while (!Thread.currentThread().isInterrupted()) {
            // Phase 1 - Selection
            Node promisingNode = selectPromisingNode(rootNode);
            // Phase 2 - Expansion
            if (promisingNode.getState().getBoard().checkStatus() == Board.IN_PROGRESS)
                expandNode(promisingNode);

            // Phase 3 - Simulation
            Node nodeToExplore = promisingNode;
            if (Thread.currentThread().isInterrupted())
                break;
            if (promisingNode.getChildHashSet().size() > 0) {
                nodeToExplore = promisingNode.getChildNode();
            }

            if (Thread.currentThread().isInterrupted())
                break;
            int playoutResult = simulatePlayout(nodeToExplore);
            // Phase 4 - Update

            if (Thread.currentThread().isInterrupted())
                break;
            backPropogation(nodeToExplore, playoutResult);
        }
        Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);
        return winnerNode.getState().getBoard();
    }

    private Node selectPromisingNode(Node rootNode) {
        Node node = rootNode;
        while (node.getChildHashSet().size() > 0 || !Thread.currentThread().isInterrupted()) {
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }

    public Board getBackUpBoard() {
        return rootNode.getState().getBoard();
    }

    private void expandNode(Node node) {
        HashSet<State> possibleStates = node.getState().getAllPossibleStates();
        possibleStates.forEach(state -> {
            Node newNode = new Node(state);
            newNode.setParent(node);
            newNode.getState().setPlayerNo(node.getState().getOpponent());
            node.getChildHashSet().add(newNode);
        });
    }

    private void backPropogation(Node nodeToExplore, int playerNo) {
        Node tempNode = nodeToExplore;
        while (tempNode != null || !Thread.currentThread().isInterrupted()) {
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
