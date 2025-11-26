package Pieces;


import Board.Tile;

import java.util.List;

public abstract class Piece extends Tile {
    // will act as a decorator for tiles
    private final Color color;
    private final Tile inner;

    Piece(Tile tile, Color color) {
        super(tile);
        this.inner = tile;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Tile unwrap(){
        return inner;
    }

    public List<Tile> getPossibleMoves(){
        // use a strategy pattern here?
        return null;
    }
}
