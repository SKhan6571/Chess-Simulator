package renderer;

import board.Board;

public interface Renderer {
    // The renderer will assume that the board is located at a constant location in memory, set by 'setBoard()'.

    void render();
    void clear();

    void setBoard(Board board);
    java.awt.Component getComponent(); // for mouse attaching mouse input (null if not applicable)
}
