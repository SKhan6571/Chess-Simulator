package renderer;

import board.Board;

public interface Renderer {
    // The renderer will assume that the board is located at a constant location in memory, set by 'setBoard()'.
    // The update() and render() methods will be called every frame. May add a wrapper that calls both.

    void render(); // call update() before render()
    void update();
    void clear();

    void setBoard(Board board);
}
