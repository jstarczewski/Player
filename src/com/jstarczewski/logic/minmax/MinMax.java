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

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.isEmpty(i, j, i, j + 1)) {
                    elements.add(new Element(i, j));
                    elements.add(new Element(i, j + 1));
                    return elements;
                }
            }
        }
        return elements;
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
        listOfBoardsOfPossibleMoves.forEach(n -> {
            Node newNode = new Node(n);
            node.addChild(newNode);
            if (isSpace(newNode.getBoard())) {
                constructTree(newNode);
            }

        });
    }


    public ArrayList<Element> getOptimalMoveData(Board board) {
        //constructTree(board);
        ArrayList<Element> elements = new ArrayList<>();
        Node root = new Node(board);
        constructTree(root);
        findWinningBranch(root);
        elements.addAll((root.getBoard()).find(board.getMoveIndex() - 1));
        return elements;
    }


    private void findWinningBranch(Node node) {
        List<Node> children = node.getChildren();
        children.forEach(child -> {
            if (!isSpace(child.getBoard())) {
                child.setScore(child.getBoard().getMoveIndex());
            } else {
                findWinningBranch(child);
            }
        });
        Node bestChild = findBestChild(children);
        node.setBoard(bestChild.getBoard());
        node.setScore(bestChild.getScore() - 1);
    }

    private Node findBestChild(List<Node> children) {
        /*
        Comparator<Node> scoreComparator = Comparator.comparing(Node::getScore).reversed();
        children.sort(scoreComparator);
        for (Node node : children) {
            if (isPlayerEven && (node.getScore() % 2 == 0)) {
                return node;
            }
            if (!isPlayerEven && (node.getScore() % 2 != 0)) {
                return node;
            }
        }
        return currentNode;
*/
        Comparator<Node> byNumberComparator = Comparator.comparing(Node::getScore);
        return children.stream()
                .max(isPlayerEven ? byNumberComparator.reversed() : byNumberComparator)
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
