package com.jstarczewski.logic.minmax;

import com.jstarczewski.board.Board;
import com.jstarczewski.board.Element;
import com.jstarczewski.logic.Algorithm;

import java.util.*;

public class MinMax implements Algorithm {

    private Tree tree;
    private boolean isPlayerEven = true;


    private boolean isSpace(Board board) {
        int ai, aj;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                ai = (i == 0) ? board.getSize() - 1 : i - 1;
                aj = (j == 0) ? board.getSize() - 1 : j - 1;
                if (board.isEmpty(i, j, ai, j)) {
                    return true;
                }
                if (board.isEmpty(i, j, i, aj)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<Element> getStartMoveData(Board board) {
        ArrayList<Element> elements = new ArrayList<>();
        int ai, aj;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                ai = i == 0 ? board.getSize() - 1 : i - 1;
                aj = j == 0 ? board.getSize() - 1 : j - 1;
                if (board.isEmpty(i, j, ai, j)) {
                    elements.add(new Element(i, j));
                    elements.add(new Element(ai, j));
                    return elements;
                }
                if (board.isEmpty(i, j, i, aj)) {
                    elements.add(new Element(i, j));
                    elements.add(new Element(i, aj));
                    return elements;
                }
            }
        }
        return elements;
    }


    private Board getPossibleBlockPositions(Board board) {
        Board board1 = new Board();
        int ai, aj;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                ai = i == 0 ? board.getSize() - 1 : i - 1;
                aj = j == 0 ? board.getSize() - 1 : j - 1;
                if (board.isEmpty(i, j, ai, j)) {
                    board1 = new Board(board, i, j, ai, j);
                }
                if (board.isEmpty(i, j, i, aj)) {
                    board1 = new Board(board, i, j, i, aj);
                }
            }
        }
        return board1;
    }

    private ArrayList<Board> getAllPossibleBlockPositions(Board board) {
        ArrayList<Board> boards = new ArrayList<>();
        int ai, aj;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                ai = i == 0 ? board.getSize() - 1 : i - 1;
                aj = j == 0 ? board.getSize() - 1 : j - 1;
                if (board.isEmpty(i, j, ai, j)) {
                    boards.add(new Board(board, i, j, ai, j));
                }
                if (board.isEmpty(i, j, i, aj)) {
                    boards.add(new Board(board, i, j, i, aj));
                }
            }
        }
        return boards;
    }


    private void constructTree(Board board) {
        this.tree = new Tree();
        Node root = new Node(board);
        tree.setRoot(root);
        constructTree(root);
    }


    private void constructTree(Node node) {
        List<Board> listOfBoardsOfPossibleMoves = getAllPossibleBlockPositions(node.getBoard());
        /*
        listOfBoardsOfPossibleMoves.forEach(n -> {
            Node newNode = new Node(n);
            node.addChild(newNode);
            if (isSpace(newNode.getBoard())) {
                constructTree(newNode);
            }
            if (newNode.getBoard().getMoveIndex() % 2 == 0) {
                return newNode;
            }
        });
        */
        for (Board b : listOfBoardsOfPossibleMoves) {
            Node newNode = new Node(b);
            node.addChild(newNode);
            if (isSpace(newNode.getBoard())) {
                constructTree(newNode);
            }
            if (newNode.getBoard().getMoveIndex() % 2 == 0) {
                return;
            }
        }
    }

    public ArrayList<Element> getOptimalMoveData(Board board) {
        constructTree(board);
        ArrayList<Element> elements = new ArrayList<>();
        Node root = tree.getRoot();
        try {
            findWinningBranch(root);
            elements.addAll((root.getBoard()).find(board.getMoveIndex()));
        } catch (NoSuchElementException e) {
            return elements;
        }
        return elements;
    }


    private void findWinningBranch(Node node) {
        List<Node> children = node.getChildren();
        for (Node child : children) {
            if (!isSpace(child.getBoard())) {
                child.setScore(child.getBoard().getMoveIndex());
            } else {
                findWinningBranch(child);
            }
        }
        Node bestChild = findBestChild(children);
        if (bestChild != null) {
            node.setBoard(bestChild.getBoard());
            node.setScore(bestChild.getScore());
        }
    }

    private Node findBestChild(List<Node> children) {
        Comparator<Node> byScoreComparator = Comparator.comparing(Node::getScore);
        return children.stream()
                //  .filter(isPlayerEven ? node -> node.getScore() % 2 == 0 : node -> node.getScore() % 2 != 0)
                .max(byScoreComparator)
                .orElseThrow(NoSuchElementException::new);
    }

    public Tree getTree() {
        return tree;
    }

    @Override
    public void setPlayerEven(boolean playerEven) {
        isPlayerEven = playerEven;
    }
}
