package pieces;
class King extends Piece { 
    public King(Color c) { super(c); } 

    @Override
    protected int[][] getMoveDirections() {
        return new int[][] { {1, 0}, {0, 1}, {1, 1} };
    }
    @Override
    protected boolean isSliding() { return false; }

    // for the display of the piece
    @Override
    public String toString() {
        //ascii code for chess pieces
        return this.getColor() == Color.Black ? "\u2654" : "\u265A";
    }
}