package board;
import pieces.Piece;

public class Tile {
    private Piece piece;
    protected int rank;
    protected int file;

    public Tile(int rank, int file) {
        this.rank = rank;
        this.file = file;
        this.piece = null;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
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
}