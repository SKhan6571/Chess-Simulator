package renderer;
import java.util.List;
import java.util.ArrayList;
import board.Board;
import board.Tile;

public class BoardPrinter {
    private static final int firstRankAndCollumn = 0;
    private static final int finalRankAndCollumn = 7;

    ///////////WARNNING : Due to unicode decisions the white pieces are technicallly "black" in the unicode and vice versa but they display correctly

    ////////// DEPENDENCY INJECTION!!!!!!!!!!!!!!!!!!!!!
    //// default function uses an empty array for the highlighted moves if theres no selected piece
    public static void printBoard(Board board) {
        printBoard(board, new ArrayList<>());
    }



    public static void printBoard(Board board, List<Tile> highlights) {


        //newline for spacing
        System.out.println();
        System.out.println("   ╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗");


        //start high rank and go down to low rank since high ranks are black side and should appear at the top
        for (int rank = finalRankAndCollumn; rank >= firstRankAndCollumn; rank--) {
        //goes through all tiles in a rank

            //Rank labels
            System.out.print(" " + (rank + 1) + " ║");


            //goes through all tiles in a collumn
            for (int file = firstRankAndCollumn; file <= finalRankAndCollumn; file++) {
                Tile tile = board.getTile(rank, file);
                
                if (highlights.contains(tile)) {
                    System.out.print(" X "); // Print X if it's a valid move
                } 
                else if (tile.isOccupied()) {
                    System.out.print(' ' + tile.getPiece().toString() + ' ');
                } else {
                    System.out.print("   ");
                }

                System.out.print('║');

            }
            // Newline after every rank
            System.out.println();
            if (rank != firstRankAndCollumn) {
                System.out.println("   ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣");
            }
        }
        
        // Print column labels
        System.out.println("   ╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝");
        System.out.println("     a   b   c   d   e   f   g   h");
        //newline for spacing
        System.out.println();
    }
}