package pieces;
class Knight extends Piece { 
    public Knight(Color c) { super(c); } 
    @Override
    public String toString() {
        return this.getColor() == Color.White ? "K" : "k";
    }
}