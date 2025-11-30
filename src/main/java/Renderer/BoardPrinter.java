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
        //top border of the board
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("    ______ __  __ ______  _____  _____ ");
        System.out.println("   / ____// / / // ____/ / ___/ / ___/ ");
        System.out.println("  / /    / /_/ // __/    \\__ \\  \\__ \\  ");
        System.out.println(" / /___ / __  // /___   ___/ / ___/ /  ");
        System.out.println(" \\____//_/ /_//_____/  /____/ /____/   ");
        System.out.println();
        System.out.println("        Welcome to Chess in Java!");
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
                
                //checks if the tile is a possible move to highlight
                if (highlights.contains(tile)) {
                    if(!tile.isOccupied()) {
                        System.out.print("«¤»"); // Prints «¤» if it's a valid move and not occupied
                    } else {
                        System.out.print("«" + tile.getPiece().toString() + "»"); // Print «X» if it's a valid capture
                    }
                } // else if the tile is occupied print the piece
                else if (tile.isOccupied()) {
                    System.out.print(' ' + tile.getPiece().toString() + ' ');
                } else { 
                    //housekeeping for empty tiles
                    System.out.print("   ");
                }
                //print vertical separator between tiles
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