import board.Board;
import board.Tile;
import game.Command;
import game.Game;
import input.InputHandler;
import pieces.Color;
import pieces.Piece;
import renderer.BoardPrinter;
import renderer.ConsoleGameObserver;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;


//main driver class for the chess game
//To run the game, enter this in terminal: 'java Chess.java'
//at any time during the game, type 'quit' to exit
public class Chess {
    public static void main(String[] args) {

        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.err.println("Could not set UTF-8 encoding");
        }

        // Singleton: Get the single instance of Game
        Game game = Game.getInstance();

        // Observer: Register the console renderer to the board
        // This ensures the board prints automatically whenever a move executes or undoes
        game.getBoard().addObserver(new ConsoleGameObserver());

        // Initial Print
        BoardPrinter.printBoard(game.getBoard());

        //open scanner for user input
        Scanner scanner = new Scanner(System.in);

        //primary game loop
        while(true) {
            Color currentColor = game.getCurrentTurnColor();
            Board board = game.getBoard();

            // Checkmate / Check Detection
            if (board.isInCheck(currentColor)) {
                if (board.isCheckmate(currentColor)) {
                    System.out.println("\u001B[31mCHECKMATE! " + (currentColor == Color.White ? "Black" : "White") + " wins!\u001B[0m");
                    break;
                }
                System.out.println("\u001B[33mCHECK!\u001B[0m");
            } else {
                // Optional: Stalemate check
                if (!board.hasLegalMoves(currentColor)) {
                    System.out.println("\u001B[33mSTALEMATE! The game is a draw.\u001B[0m");
                    break;
                }
            }

            //two moves = one full game turn. white and black alternate moves. white goes first.
            System.out.println("Turn " + game.getTurnNumber() + ": It is " + game.getCurrentTurnColor() + "'s move.");

            // 1. Select Piece (Handle Undo inside here)
            Tile selectedTile = InputHandler.promptForSourceTile(scanner, game);

            // Null means user typed 'undo'.
            // If undo, the board already updated via Observer, so we just loop again.
            if (selectedTile == null) {
                continue;
            }

            // We return a dummy tile (with rank and file equal to -1) if user typed
            // 'quit' so we can break from the loop and exit
            if (selectedTile.getRank() == -1 && selectedTile.getFile() == -1) {
                break;
            }

            //otherwise try to get the piece on the selected tile
            Piece selectedPiece = selectedTile.getPiece();

            // Highlight moves (Direct call still okay for temporary UI state)
            List<Tile> possibleMoves = selectedPiece.getLegalMoves(game.getBoard(), selectedTile);
            BoardPrinter.printBoard(game.getBoard(), possibleMoves);

            // 2. Select Destination and Create Command
            Command moveCommand = InputHandler.promptForDestination(scanner, game, selectedTile, possibleMoves);

            if (moveCommand != null) {
                // Command: Execute via Invoker (Game)
                game.executeMove(moveCommand);
                // The Observer triggers the Board Print here automatically
            }
        }
    }
}