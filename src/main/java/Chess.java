import board.Board;
import board.Tile;
import renderer.BoardPrinter;
import java.util.Scanner;
import input.InputHandler;
import pieces.Piece;
import java.util.List;
import pieces.Color;
import input.InputHandler;


//main driver class for the chess game
public class Chess {
    public static void main(String[] args) {
        int moveCount = 1; // odd = white's turn, even = black's turn
        int turnNumber = moveCount;

        /// Initialize a new chess board
        /// ///simple ascii board print test
            ////////USES DEPENDENCY INJECTION!!!!!!!!!!!!!!!
        Board board = new Board();
        BoardPrinter.printBoard(board);
        Scanner scanner = new Scanner(System.in);

        //primary game loop
        while(true){
            //calculate turn info
            turnNumber = (moveCount + 1) / 2;
            //determine who's turn it is. odd is white even is black
            Color currentTurnColor = (moveCount % 2 != 0) ? Color.White : Color.Black;
            //store turns seperate from moves to keep track of who is moving and what color they are
            System.out.println("Turn " + turnNumber + ": It is " + currentTurnColor + "'s move.");

            Tile selectedTile = InputHandler.promptForSourceTile(scanner, board, currentTurnColor);

            // when user types quit it equals null and we can exit the game
            if (selectedTile == null) break;

            //display selected piece
            Piece selectedPiece = selectedTile.getPiece();
            System.out.println("Selected Piece: " + selectedPiece.toString());

            //display possible moves
            List<Tile> possibleMoves = selectedPiece.getPossibleMoves(board, selectedTile);
            BoardPrinter.printBoard(board, possibleMoves);

            //increment move count if a move was made
            boolean moveWasMade = InputHandler.promptForDestination(scanner, board, selectedTile, possibleMoves);
            if (moveWasMade) { moveCount++; }
        }
        scanner.close();
    }
}