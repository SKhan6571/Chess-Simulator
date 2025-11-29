package pieces;
class King extends Piece { 
    public King(Color c) { super(c); } 
    @Override
    public String toString() {
        return this.getColor() == Color.White ? "K" : "k";
    }
}