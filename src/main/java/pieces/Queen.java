package pieces;
class Queen extends Piece { 
    public Queen(Color c) { super(c); } 

    @Override
    protected int[][] getMoveDirections() {
        return new int[][] { {1, 0}, {0, 1}, {1, 1} };
    }
    @Override
    protected boolean isSliding() { return true; }

    // for the dipslay of the pieces
    @Override
    public String toString() {
        return this.getColor() == Color.Black ? "\u2655" : "\u265B";
    }
}