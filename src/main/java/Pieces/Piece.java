package pieces;


import java.util.List;

import board.Tile;

public abstract class Piece {
    protected final Color color;

    public Piece(Color color) {
        this.color = color;
    }
    
    public Color getColor() { return color; }
    
    //public abstract List<Tile> getPossibleMoves(Tile currentTile);
}

/*
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
*/