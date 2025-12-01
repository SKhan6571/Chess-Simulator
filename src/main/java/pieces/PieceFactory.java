package pieces;

public class PieceFactory {

    // helper method for back rank pieces
    private static Piece createBackRank(int file, Color color) {
        switch (file) {
            case 0: return new Rook(color);
            case 1: return new Knight(color);
            case 2: return new Bishop(color);
            case 3: return new Queen(color); 
            case 4: return new King(color);
            case 5: return new Bishop(color);
            case 6: return new Knight(color);
            case 7: return new Rook(color);
            default: throw new RuntimeException("Invalid file");
        }
    }

    public static Piece createPiece(int rank, int file) {

        // chooses proper color and pawn or color and back rank piece
        
        if (rank == 1) {
            return new Pawn(Color.White);
        } else if (rank == 6) {
            return new Pawn(Color.Black);
        } else if (rank == 0) {
            return createBackRank(file, Color.White);
        } else if (rank == 7) {
            return createBackRank(file, Color.Black);
        }
        
        // Return null for empty rows
        return null;
    }
}