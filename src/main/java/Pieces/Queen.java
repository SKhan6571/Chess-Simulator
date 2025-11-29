package pieces;
class Queen extends Piece { 
    public Queen(Color c) { super(c); } 
    @Override
    public String toString() {
        return this.getColor() == Color.White ? "Q" : "q";
    }
}