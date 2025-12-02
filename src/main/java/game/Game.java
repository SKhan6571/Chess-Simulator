package game;

import board.Board;
import pieces.Color;

import java.util.Stack;

public class Game {
    // Singleton Instance
    private static Game instance;

    private final Board board;
    private int moveCount;

    // Command History
    private final Stack<Command> history = new Stack<>();

    // Private constructor
    private Game() {
        this.board = new Board();
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
}