package pieces;

class Rook extends Piece { 
    public Rook(Color c) { super(c); } 

    @Override
    protected int[][] getMoveDirections() {
        return new int[][] { {1, 0}, {0, 1} };
    }

    @Override
    protected boolean isSliding() { return true; }

    // for the dipslay of the piece
    @Override
    public String toString() {
        // asciii code for chesss pieces
        return this.getColor() == Color.Black ? "\u2656" : "\u265C";
    }
}
