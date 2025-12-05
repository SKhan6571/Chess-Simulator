package board;
import pieces.Piece;

import pieces.Color;

public class Tile {
    private Piece piece;
    protected int rank;
    protected int file;

    public Tile(int rank, int file) {
        this.rank = rank;
        this.file = file;
        this.piece = null;
    }

    public Piece setPiece(Piece piece) {
        this.piece = piece;
        return piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public boolean isOccupied() {
        return this.piece != null;
    }

    public int getRank() {
        return this.rank;
    }

    public int getFile() {
        return this.file;
    }

    public Color getColor() {
        return ((file + rank) % 2 == 0) ? Color.White : Color.Black;
    }

    public String toString() {
        char fileLetter = (char) ('a' + file);
        int rankNumber = 1 + rank;
        return "" + fileLetter + rankNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tile objTile)) return false;
        return (this.rank == objTile.getRank()) && (this.file == objTile.file);
    }
}