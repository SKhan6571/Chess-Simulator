package input;

import board.Board;
import board.Tile;
import game.Command;
import game.Game;
import game.MoveCommand;
import pieces.Color;
import pieces.Piece;
import renderer.BoardPrinter;

import java.util.List;
import java.util.Scanner;

//This is depriciated, and should be replaced with the an implementation of InputHandler
public class KeyboardInputHandler {
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

    // Returns a Tile to move FROM, or null if quitting/undoing
    public static Tile promptForSourceTile(Scanner scanner, Game game) {
        Board board = game.getBoard();
        Color currentTurnColor = game.getCurrentTurnColor();

        while (true) {
            System.out.print("Enter piece to move (e.g. 'e2'), 'undo', or 'quit': ");
            String rawInput = scanner.nextLine();

            // secret option to exit
            if (rawInput.equals("q") || rawInput.equals("quit") || rawInput.equals("exit")) {
                return null;
            }

            // Hook for Undo
            if (rawInput.equals("undo")) {
                game.undoLastMove();
                // Reprint board after undo since we are still in this loop
                return null; // Return null to restart loop in Main
            }

            //use try catch to handle invalid input and restart loop
            try {
                int[] pieceLocation = KeyboardInputHandler.parseInput(rawInput);
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
                System.out.println("Error: Invalid input.");
            }
        }
    }

    // Returns a Command to execute, or null if cancelled
    public static Command promptForDestination(Scanner scanner, Game game, Tile startTile, List<Tile> allowedMoves) {
        Board board = game.getBoard();
        Piece pieceToMove = startTile.getPiece();

        while (true) {
            System.out.print("Enter destination for " + pieceToMove + " (or 'cancel'): ");
            String destInput = scanner.nextLine();

            if (destInput.equals("cancel")) {
                BoardPrinter.printBoard(board); // Re-print clean board
                return null;
            }

            //use try catch to handle invalid input
            try {
                //  parse input and get target tile
                int[] destCoords = KeyboardInputHandler.parseInput(destInput);
                Tile targetTile = board.getTile(destCoords[0], destCoords[1]);

                if (allowedMoves.contains(targetTile)) {
                    // Create the command instead of executing directly
                    return new MoveCommand(game, startTile, targetTile);
                } else {
                    System.out.println("Invalid move! That tile is not highlighted. Try again!");
                }
            } catch (Exception e) {
                System.out.println("Invalid destination format.");
            }
        }
    }
}
