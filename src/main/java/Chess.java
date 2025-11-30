import board.Tile;
import renderer.BoardPrinter;
import java.util.Scanner;
import input.InputHandler;
import pieces.Piece;
import java.util.List;
import game.Game;


//main driver class for the chess game
//To run the game, enter this in terminal: 'java Chess.java'
//at any time during the game, type 'quit' to exit
public class Chess {
    public static void main(String[] args) {
        /// creates a new game instance
        Game game = new Game(); 
        
        //////prints simple ascii board for development purposes
        BoardPrinter.printBoard(game.getBoard());
        Scanner scanner = new Scanner(System.in);

        //primary game loop
        while(true) {
            //two moves = one full game turn. white and black alternate moves. white goes first.
            System.out.println("Turn " + game.getTurnNumber() + ": It is " + game.getCurrentTurnColor() + "'s move.");

            //ask user to pick a piece to move
            Tile selectedTile = InputHandler.promptForSourceTile(scanner, game.getBoard(), game.getCurrentTurnColor());

            // if user typed quit then we can exit the game with break
            if (selectedTile == null) break;

            //otherwise try to get the piece on the selected tile
            Piece selectedPiece = selectedTile.getPiece();

            //print board with all legal/possible moves highlighted
            List<Tile> possibleMoves = selectedPiece.getPossibleMoves(game.getBoard(), selectedTile);
            BoardPrinter.printBoard(game.getBoard(), possibleMoves);

            //prompt user to pick a destination for the piece to move to
            boolean moveWasMade = InputHandler.promptForDestination(scanner, game.getBoard(), selectedTile, possibleMoves);
            //if piece was moved successfully then increment move count
            if (moveWasMade) { game.incrementMove(); }
        }
        scanner.close();
    }
}