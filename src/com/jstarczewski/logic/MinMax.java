package com.jstarczewski.logic;

import com.jstarczewski.board.Board;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class MinMax {

    private Tree tree;

    public void constructTree(Board board) {
        tree = new Tree();
        Node root = new Node(board, true);
        tree.setRoot(root);
        constructTree(root);
    }

    private void constructTree(Node node) {
        List<Board> listOfBoardsOfPossibleMoves = getAllPossibleBlockPositions(node.getBoard());
        boolean isEvenPlayer = !node.isEvenPlayer();
        listOfBoardsOfPossibleMoves.forEach(n -> {

            Node newNode = new Node(n, isEvenPlayer);
            node.addChild(newNode);
            if (isSpace(newNode.getBoard())) {
                constructTree(newNode);
            }

        });
    }

    public void makeMove(int moveIndex, Board board) {
        getAllPossibleBlockPositions(board);


    }

    private boolean isSpace(Board board) {

        int ai, aj;
        for (int i = 0; i < board.getSize(); i++) {

            for (int j = 0; j < board.getSize(); j++) {
                ai = i;
                aj = j;
                if (i == 0)
                    ai = board.getSize() - 1;
                if (j == 0)
                    aj = board.getSize() - 1;
                if (board.isEmpty(i, j, ai, j)) {
                    return true;
                }
                //blocks.add(new Block((new Element(i, j)), (new Elemeont(ai, j))));

                if (board.isEmpty(i, j, i, aj)) {
                    return true;
                }
                //     blocks.add(new Block((new Element(i, j)), (new Element(i, aj))));

            }
        }
        return false;

    }

    private ArrayList<Board> getAllPossibleBlockPositions(Board board) {
        ArrayList<Board> boards = new ArrayList<>();
        int ai, aj;
        for (int i = 0; i < board.getSize(); i++) {

            for (int j = 0; j < board.getSize(); j++) {
                ai = i;
                aj = j;
                if (i == 0)
                    ai = board.getSize() - 1;
                if (j == 0)
                    aj = board.getSize() - 1;
                if (board.isEmpty(i, j, ai, j)) {
                    Board tempBoard = new Board();
                    tempBoard.setMoveIndex(board.getMoveIndex());
                    tempBoard.setBoardSize(board.getSize());
                    tempBoard.copy(board);

                    tempBoard.fillField(i, j, board.getMoveIndex() + 1);
                    tempBoard.fillField(ai, j, board.getMoveIndex() + 1);
                    boards.add(tempBoard);
                }
                //blocks.add(new Block((new Element(i, j)), (new Elemeont(ai, j))));

                if (board.isEmpty(i, j, i, aj)) {
                    Board tempBoard = new Board();
                    tempBoard.setMoveIndex(board.getMoveIndex());
                    tempBoard.setBoardSize(board.getSize());
                    tempBoard.copy(board);

                    tempBoard.fillField(i, j, board.getMoveIndex() + 1);
                    tempBoard.fillField(i, aj, board.getMoveIndex() + 1);
                }
                //     blocks.add(new Block((new Element(i, j)), (new Element(i, aj))));

            }
        }
        return boards;
    }

    public boolean checkWin() {
        Node root = tree.getRoot();
        checkWin(root);
        return root.getScore() % 2 == 0;
    }

    private void checkWin(Node node) {
        List<Node> children = node.getChildren();
        boolean isEvenPlayer = node.isEvenPlayer();
        children.forEach(child -> {
            if(!isSpace(child.getBoard())) {
                child.setScore(child.getBoard().getMoveIndex());
            } else {
                checkWin(child);
            }
        });
        Node bestChild = findBestChild(isEvenPlayer, children);
        node.setScore(bestChild.getScore());
    }
    private Node findBestChild(boolean isEvenPlayer, List<Node> children) {
        Comparator<Node> byNumberComparator = Comparator.comparing(Node::getScore);
        return children.stream()
                .max(isEvenPlayer ? byNumberComparator : byNumberComparator.reversed())
                .orElseThrow(NoSuchElementException::new);
    }

}
