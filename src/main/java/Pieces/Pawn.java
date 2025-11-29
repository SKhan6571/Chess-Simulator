package pieces;
class Pawn extends Piece { 
    public Pawn(Color c) { super(c); } 
    @Override
    public String toString() {
        return this.getColor() == Color.White ? "P" : "p";
    }
}