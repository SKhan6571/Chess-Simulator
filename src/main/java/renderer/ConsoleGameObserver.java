package renderer;

import board.Board;

// This observer prints the board when its state changes automatically
public class ConsoleGameObserver extends GameObserver {
    @Override
    public void onBoardUpdate(Board board) {
        if (!highlightedTiles.isEmpty()) {
            BoardPrinter.printBoard(board, highlightedTiles);
        }
        else BoardPrinter.printBoard(board);
    }
}