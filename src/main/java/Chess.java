import board.Tile;
import game.Command;
import game.Game;
import input.InputHandler;
import input.KeyboardInputHandler;
import pieces.Piece;
import renderer.BoardPrinter;
import renderer.ConsoleGameObserver;
//import renderer.JOGLFacade;
import renderer.JOGLGameObserver;
import renderer.Renderer;

import java.util.List;
import java.util.Scanner;


//main driver class for the chess game
//To run the game, enter this in terminal: 'java Chess.java'
//at any time during the game, type 'quit' to exit
public class Chess {
    public static void main(String[] args) {
        // Singleton: Get the single instance of Game
        Game game = Game.getInstance();


        // Observer: Register the console renderer to the board
        // This ensures the board prints automatically whenever a move executes or undoes
        game.getBoard().addObserver(new ConsoleGameObserver());
        game.getBoard().addObserver(new JOGLGameObserver(game));

        // Initial Print
        BoardPrinter.printBoard(game.getBoard());

        //open scanner for user input
        Scanner scanner = new Scanner(System.in);

        //primary game loop
        while(true) {
            //two moves = one full game turn. white and black alternate moves. white goes first.
            System.out.println("Turn " + game.getTurnNumber() + ": It is " + game.getCurrentTurnColor() + "'s move.");

            // 1. Select Piece (Handle Undo inside here)
            Tile selectedTile = KeyboardInputHandler.promptForSourceTile(scanner, game);

            // Null means user typed 'quit' or 'undo'.
            // If undo, the board already updated via Observer, so we just loop again.
            if (selectedTile == null) {
                continue;
            }

            //otherwise try to get the piece on the selected tile
            Piece selectedPiece = selectedTile.getPiece();

            // Highlight moves (Direct call still okay for temporary UI state)
            List<Tile> possibleMoves = selectedPiece.getPossibleMoves(game.getBoard(), selectedTile);
            BoardPrinter.printBoard(game.getBoard(), possibleMoves);

            // 2. Select Destination and Create Command
            Command moveCommand = KeyboardInputHandler.promptForDestination(scanner, game, selectedTile, possibleMoves);

            if (moveCommand != null) {
                // Command: Execute via Invoker (Game)
                game.executeMove(moveCommand);
                // The Observer triggers the Board Print here automatically
            }
        }
    }
}