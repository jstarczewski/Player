package com.jstarczewski.logic.minmax;

import com.jstarczewski.logic.minmax.board.Block;
import com.jstarczewski.logic.minmax.board.Board;
import com.jstarczewski.logic.minmax.board.Element;
import com.jstarczewski.logic.Algorithm;

import java.util.*;

public class MinMax implements Algorithm {

    private Tree tree;
    private boolean isPlayerEven = true;
    private Board tempBoard;
    int moveIndex;
    private Board board;
    int nextMoveIndex;

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

    private ArrayList<Block> getAllPossibleBlockPositions(Board board) {
        ArrayList<Block> blocks = new ArrayList<>();
        int ai, aj;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                ai = i == 0 ? board.getSize() - 1 : i - 1;
                aj = j == 0 ? board.getSize() - 1 : j - 1;
                if (board.isEmpty(i, j, ai, j)) {
                    blocks.add(new Block(i, j, ai, j));
                }
                if (board.isEmpty(i, j, i, aj)) {
                    blocks.add(new Block(i, j, i, aj));
                }
            }
        }
        return blocks;
    }


    private void constructTree(Block block) {
        this.tree = new Tree();
        Node root = new Node(block);
        tree.setRoot(root);
        constructTree(root);
    }


    private void constructTree(Node node) {
        List<Block> listOfBoardsOfPossibleMoves = getAllPossibleBlockPositions(tempBoard);
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

        for (Block b : listOfBoardsOfPossibleMoves) {
            int cords[] = b.getBlockCordinates();
            tempBoard.fillField(cords[0], cords[1], cords[2], cords[3]);
            //Board tempBoard = new Board(no, cords[0], cords[1], cords[2], cords[3]);
            Node newNode = new Node(b);
            newNode.setScore(tempBoard.getMoveIndex());
            System.out.println(tempBoard.toString());
            node.addChild(newNode);
            if (isSpace(tempBoard)) {
                constructTree(newNode);
            }
            if (tempBoard.getMoveIndex() % 2 == 0) {
                moveIndex = tempBoard.getMoveIndex();
                return;
            }
            tempBoard.copy(board);
            tempBoard.setMoveIndex(board.getMoveIndex());
            moveIndex = board.getMoveIndex();
        }
    }

    public ArrayList<Element> getOptimalMoveData(Board board, Block block) {
        this.board = board;
        moveIndex = board.getMoveIndex() + 1;
        nextMoveIndex = moveIndex + 2;
        tempBoard = new Board();
        tempBoard.setBoardSize(board.getSize());
        tempBoard.copy(board);
        tempBoard.setMoveIndex(board.getMoveIndex());
        constructTree(block);
        System.out.println(moveIndex);
        ArrayList<Element> elements = new ArrayList<>();
        Node root = tree.getRoot();
        try {
            findWinningBranch(root);
            elements.addAll((root.getBlock().getBlockCordinatesByElement()));
        } catch (NoSuchElementException e) {
            return elements;
        }
        return elements;
    }


    private void findWinningBranch(Node node) {
        List<Node> children = node.getChildren();
        for (Node child : children) {
            if (!(child.getScore() == board.getMoveIndex()))
                findWinningBranch(child);
        }
        Node bestChild = findBestChild(children);
        if (bestChild != null) {
            node.setBlock(bestChild.getBlock());
        }
    }

    private Node findBestChild(List<Node> children) {
        Comparator<Node> byScoreComparator = Comparator.comparing(Node::getScore);
        return children.stream()
                //  .filter(isPlayerEven ? node -> node.getScore() % 2 == 0 : node -> node.getScore() % 2 != 0)
                .min(byScoreComparator)
                .orElseThrow(NoSuchElementException::new);
    }

    public Tree getTree() {
        return tree;
    }

    @Override
    public ArrayList<Element> getOptimalMoveData(ArrayList<Element> coordinates) {
        return null;
    }

    @Override
    public ArrayList<Element> getStartMoveData() {
        return null;
    }

    @Override
    public void setPlayer(boolean playerEven) {
        isPlayerEven = playerEven;
    }
}
