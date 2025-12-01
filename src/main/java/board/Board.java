package board;
import pieces.PieceFactory;

public class Board {
    private Tile[][] tiles;

    public Board() {
        int size = 8;
        tiles = new Tile[size][size];

        for (int rank = 0; rank < size; rank++) {
            for (int file = 0; file < size; file++) {
                // Create a blaank tile at this coordinates
                tiles[rank][file] = new Tile(rank, file);
                
                //place the pieces onto the tile - some will be null if a piece doesnt go there
                tiles[rank][file].setPiece(PieceFactory.createPiece(rank, file));


                /////UTILIZES FACTORRY PATTERN TO CREATE PIECES based on board pos!!!!!!!!!
            }
        }
    }

    public Tile getTile(int rank, int file) {
        return tiles[rank][file];
    }

    public void updateBoard() {
        renderer.BoardPrinter.printBoard(this);
    }
}