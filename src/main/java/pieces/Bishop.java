package pieces;

class Bishop extends Piece { 
    public Bishop(Color c) { super(c); } 

    @Override
    protected int[][] getMoveDirections() {
        return new int[][] { {1, 1} };
    }
    @Override
    protected boolean isSliding() { return true; }

    // for the dipslay of the piece
    @Override
    public String toString() {
        return this.getColor() == Color.Black ? "\u2657" : "\u265D";
    }
}