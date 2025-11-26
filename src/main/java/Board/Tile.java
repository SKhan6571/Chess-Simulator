package Board;

import Pieces.Piece;
import Pieces.Color;

public class Tile {// not sure if this needs to be expanded...
    private final int rank;
    private final int file;
    private final Color color;

    public Tile(Tile tile){
        this(tile.getRank(), tile.getFile(), tile.getColor());
    }

    public Tile(int rank, int file, Color color) {
        this.rank = rank;
        this.file = file;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getRank() {
        return rank;
    }
    public int getFile() {
        return file;
    }

    public boolean isOccupied() {
        return false;
    }

    public Piece getPiece(){
        return null;
    }
}
