package renderer;

import board.Board;
import board.Tile;

import java.util.ArrayList;
import java.util.List;

// Observers receive an update whenever the subject (board) changes state
public abstract class GameObserver {
    protected List<Tile> highlightedTiles = new ArrayList<>();

    public abstract void onBoardUpdate(Board board);
    public void highlightTiles(List<Tile> tiles){
        this.highlightedTiles = tiles;
    }
}
