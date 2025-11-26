package Renderer;

import Board.Board;

public interface Renderer {
    // The renderer will assume that the board is located at a constant location in memory, set by 'setBoard()'.
    // The update() and display() methods will be called every frame.
    // render() wraps both for convenience. But they are provided separately for flexibility.

    void display(); // call update() before display()
    void update();
    void render();
    void clear();

    void setBoard(Board board);
}
