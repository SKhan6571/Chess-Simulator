package renderer;

import board.Board;

// This observer prints the board when its state changes automatically
public class ConsoleGameObserver implements GameObserver {
    @Override
    public void onBoardUpdate(Board board) {
        BoardPrinter.printBoard(board);
    }
}