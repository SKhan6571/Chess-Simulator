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

import static java.lang.System.exit;

//This is depriciated, and should be replaced with an implementation of InputHandler
public class KeyboardInputHandler extends InputHandler {

    MoveCommand moveCommand;

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

    public KeyboardInputHandler(Game game) {
        super(game);
        promptForSourceTile();
    }

    // Returns a Tile to move FROM, or null if quitting/undoing
    public void promptForSourceTile() {
        System.out.println("Turn " + game.getTurnNumber() + ": It is " + game.getCurrentTurnColor() + "'s move.");
        System.out.print("Enter piece to move (e.g. 'e2'), 'undo', or 'quit': ");
    }

    // Returns a Command to execute, or null if canceled
    public void promptForDestination() {
        String pieceToMove = start.getPiece().toString();
        System.out.print("Enter destination for " + pieceToMove);
    }

    @Override
    public void handleInput(InputEvent event) {
        if (!(event instanceof ConsoleLineEvent)) return;
        String line = ((ConsoleLineEvent) event).line();
        // secret option to exit
        if (line.equals("q") || line.equals("quit") || line.equals("exit")) {
            exit(1);
        }

        // Hook for Undo
        if (line.equals("undo")) {
            game.undoLastMove();
        }

        //cancel move
        if (line.equals("cancel")) {
            source = true;
        }
        int[] pieceLocation = KeyboardInputHandler.parseInput(line);
        Board board = game.getBoard();
        Tile target = board.getTile(pieceLocation[0], pieceLocation[1]);
        handleInputLogic(target, pieceLocation);
        if (source) {
            promptForSourceTile();
        } else {
            promptForDestination();
        }
    }
}

