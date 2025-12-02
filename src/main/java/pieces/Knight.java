package pieces;
class Knight extends Piece { 
    public Knight(Color c) { super(c); } 

    @Override
    protected int[][] getMoveDirections() {
        return new int[][] { {2, 1}, {1, 2} };
    }
    @Override
    protected boolean isSliding() { return false; }

    // for the display of the piece
    @Override
    public String toString() {
        //ascii code for chess pieces
        return this.getColor() == Color.Black ? "\u2658" : "\u265E";
    }
}