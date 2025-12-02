package renderer;

import board.Board;

// Observers receive an update whenever the subject (board) changes state
public interface GameObserver {
    void onBoardUpdate(Board board);
}
