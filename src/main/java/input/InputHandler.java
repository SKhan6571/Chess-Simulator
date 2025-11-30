package input;

import java.util.List;
import java.util.Scanner;

import board.Board;
import board.Tile;
import pieces.Color;
import pieces.Piece;
import renderer.BoardPrinter;

public class InputHandler {
    //helper function to parse standard chess notation like for the ascii board input
    public static int[] parseInput(String input) {


        //find collumn from standard chess notation like 'e2'
        char fileChar = input.charAt(0);
        int file = fileChar - 'a';       // in ascii: 'e'-'a'= (101)-(97) = 4
        
        // get the rank
        char rankChar = input.charAt(1);
        int rank = Character.getNumericValue(rankChar) - 1; // 2 - 1 = 1
        
        return new int[]{rank, file};

        
    }

    //helper method #1 for chess.java
    // returns the selected tile or null if quitting
    public static Tile promptForSourceTile(Scanner scanner, Board board, Color currentTurnColor) {
        while (true) {
            System.out.print("Enter the piece to move (example: e2, g8...): ");
            String rawInput = scanner.nextLine();

            // secret option to exit
            if (rawInput.equals("q") || rawInput.equals("quit") || rawInput.equals("exit")) {
                return null; // Returning null means we quit the game
            }

            //use try catch to handle invalid input and restart loop
            try {
                int[] pieceLocation = InputHandler.parseInput(rawInput);
                Tile target = board.getTile(pieceLocation[0], pieceLocation[1]);

                // VALIDATION CHECKS
                //is tile occupied
                if (!target.isOccupied()) {
                    System.out.println("Error: There is no piece at " + rawInput + ". Try again!");
                    continue; 
                }

                //is piece the correct color for the current turn
                Piece pieceOnTile = target.getPiece();
                if (pieceOnTile.getColor() != currentTurnColor) {
                    System.out.println("Error: That is a " + pieceOnTile.getColor() + " piece! It is " + currentTurnColor + "'s turn. Try again!");
                    continue; 
                }

                //does the piece have any valid moves
                List<Tile> moves = pieceOnTile.getPossibleMoves(board, target);
                if (moves.isEmpty()) {
                    System.out.println("Error: That piece has no valid moves! Try again!");
                    continue; 
                }

                // If we get here then the piece exists, is proper color, and has valid moves. Return the tile!
                return target;

            } catch (Exception e) {
                System.out.println("Error: Invalid input format. Try again!");
            }
        }
    }


    //helper method #2 for chess.java
    // returns true if move was made, false if move was cancelled
    public static boolean promptForDestination(Scanner scanner, Board board, Tile startTile, List<Tile> allowedMoves) {
        Piece pieceToMove = startTile.getPiece();

        while (true) {
            System.out.print("Enter destination for " + pieceToMove + " (or type 'cancel'): ");
            String destInput = scanner.nextLine();

            if (destInput.equals("cancel")) {
                //if user cancels the move then we just reprint the board without highlights and return false to reprompt for piece again
                destInput = null;
                BoardPrinter.printBoard(board);
                return false;
            }

            //use try catch to handle invalid input
            try {
                //  parse input and get target tile
                int[] destCoords = InputHandler.parseInput(destInput);
                Tile targetTile = board.getTile(destCoords[0], destCoords[1]);

                if (allowedMoves.contains(targetTile)) {
                    // EXECUTE THE MOOOVE!!!!
                    startTile.setPiece(null);
                    targetTile.setPiece(pieceToMove);
                    
                    BoardPrinter.printBoard(board);
                    return true; // Returning true means the move was made successfully
                } else {
                    System.out.println("Invalid move! That tile is not highlighted. Try again!");
                }
            } catch (Exception e) {
                System.out.println("Invalid destination format.");
            }
        }
    }
}
