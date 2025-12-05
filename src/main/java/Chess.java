import board.Board;
import board.Tile;
import game.Command;
import game.Game;
import input.*;
import pieces.Piece;
import renderer.BoardPrinter;
import renderer.ConsoleGameObserver;
//import renderer.JOGLFacade;
import renderer.JOGLGameObserver;
import renderer.Renderer;

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

        System.out.println("Welcome to Chess Simulator!");

        // Observer: Register the console renderer to the board
        // This ensures the board prints automatically whenever a move executes or undoes
        game.getBoard().addObserver(new ConsoleGameObserver());
        game.getBoard().addObserver(new JOGLGameObserver(game));

        InputHandler inputHandler = new MouseInputHandler(game);
        InputManager inputManager = new InputManager();
        new MouseInputSource(inputManager); // This registers the mouse listener

        //noinspection InfiniteLoopStatement
        while(true){
            try {
                InputEvent inputEvent = inputManager.getNextInput();
                inputHandler.handleInput(inputEvent);
            }catch (InterruptedException  e){
                Thread.currentThread().interrupt();
            }
        }
    }
}