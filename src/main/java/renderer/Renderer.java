package renderer;

import board.Board;
import board.Tile;
import game.Game;

import java.util.List;

public interface Renderer {
    // The renderer will assume that the board is located at a constant location in memory, set by 'setBoard()'.

    void render();
    void clear();

    void setGame(Game game);
    void setHighlightedTiles(List<Tile> highlightedTiles);
    java.awt.Component getComponent(); // for mouse attaching mouse input (null if not applicable)
}
