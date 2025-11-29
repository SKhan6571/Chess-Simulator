package pieces;

class Rook extends Piece { 
    public Rook(Color c) { super(c); } 
    @Override
    public String toString() {
        return this.getColor() == Color.White ? "R" : "r";
    }
}
