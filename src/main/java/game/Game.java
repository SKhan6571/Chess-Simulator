package game;

import board.Board;
import board.Tile;
import pieces.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Game {
    // Singleton Instance
    private static Game instance;

    private final Board board;
    private final List<Tile> piecesOnBoard;
    private int moveCount;

    // Command History
    private final Stack<Command> history = new Stack<>();

    // Private constructor
    private Game() {
        this.board = new Board();
        this.piecesOnBoard = new ArrayList<>();
        scanBoardForPieces();
        this.moveCount = 1;
    }

    /// // SINGLETON PATTERN (LAZY INSTANTIATION) IS USED TO MAINTAIN ONLY ONE GAME STATE!!!!

    // Singleton accessor using lazy instantiation
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /// // COMMAND PATTERN IS USED TO EXECUTE AND UNDO MOVES!!!!

    // Command Pattern: Execution
    public void executeMove(Command command) {
        command.execute();
        history.push(command);
        incrementMove();
    }

    // Command Pattern: Undo
    public void undoLastMove() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();
            decrementMove();
        } else {
            System.out.println("No moves to undo!");
        }
    }

    public int getTurnNumber() {
        return (moveCount + 1) / 2;
    }

    public Color getCurrentTurnColor() {
        return Color.fromMoveCount(moveCount);
    }

    private void incrementMove() {
        moveCount++;
    }

    private void decrementMove() {
        moveCount--;
    } // Needed for Undo

    public Board getBoard() {
        return board;
    }

    private void scanBoardForPieces() {
        piecesOnBoard.clear();
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Tile tile = board.getTile(rank, file);
                if (tile.getPiece() != null) {
                    piecesOnBoard.add(tile);
                }
            }
        }
    }

    public List<Tile> getPiecesOnBoard() {
        return piecesOnBoard;
    }

    public void removePiece(Tile tile) {
        piecesOnBoard.remove(tile);
    }

    public void addPiece(Tile tile) {
        piecesOnBoard.add(tile);
    }

    public void swapPieceTile(Tile start, Tile end){
        piecesOnBoard.set(piecesOnBoard.indexOf(start), end);
    }
}