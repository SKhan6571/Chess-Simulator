package renderer;

import board.Board;
import board.Tile;

public class BoardPrinter {


    ///////////WARNNING : BLACK PIECES LOOK WHITE AND VICE VERSA DUE TO UNICODE STANDARDS!!!!!!!

    public void printBoard(Board board) {
        //newline for spacing
        System.out.println();
        //start high rank and go down to low rank since high ranks are black side and should appear at the top
        for (int rank = 7; rank >= 0; rank--) {
            
            //Prints rank labels
            System.out.print((rank + 1) + " | ");

            for (int file = 0; file < 8; file++) {
                Tile tile = board.getTile(rank, file);
                
                if (tile.isOccupied()) {
                    // if tile is occupied, print the piece
                    System.out.print(tile.getPiece().toString());
                } else {
                    // If empty, print a placeholder
                    System.out.print("-");
                }
                
                // Add a space for readability
                System.out.print(" ");
            }
            // New line after every rank
            System.out.println();
        }
        
        // Print column labels
        System.out.println("   ================");
        System.out.println("    a b c d e f g h");
        //newline for spacing
        System.out.println();
    }
}