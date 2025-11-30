package game;

import board.Board;
import pieces.Color;

public class Game {
    private final Board board;
    private int moveCount;

    public Game() {
        this.board = new Board();
        this.moveCount = 1;
    }

    public int getTurnNumber() {
        return (moveCount + 1) / 2;
    }

    public Color getCurrentTurnColor() {
        return Color.fromMoveCount(moveCount);
    }

    public void incrementMove() {
        moveCount++;
    }

    public Board getBoard() {
        return board;
    }
}