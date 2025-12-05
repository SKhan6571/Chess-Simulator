package input;

import board.Board;
import board.Tile;
import game.Game;
import game.MoveCommand;
import pieces.Color;
import pieces.Piece;

import java.util.List;

public abstract class InputHandler {
    Game game;
    Tile start;
    List<Tile> allowedMoves;

    boolean source = true;

    public InputHandler(Game game) {
        this.game = game;
    }

    public abstract void handleInput(InputEvent event);

    public void highLightTiles(List<Tile> tiles){
        game.getBoard().highlightTiles(tiles);
    }

    /// This function handles the actual logic of checking if the input is valid, the abstract method calls this method
    /// This is a general method, and should not be expected to handle any quirks of a certain source.
    protected void handleInputLogic(Tile target,int[] coords){
        // parse input and verify it is valid
        try {
            Board board = game.getBoard();
            Color currentTurnColor = game.getCurrentTurnColor();

            // check validity of source input
            if (source) {
                // VALIDATION CHECKS
                //is the tile occupied
                if (!target.isOccupied()) {
                    System.out.println("Error: There is no piece at " + target.toString() + ". Try again!");
                    return;
                }

                //is the piece the correct color for the current turn?
                Piece pieceOnTile = target.getPiece();
                if (pieceOnTile.getColor() != currentTurnColor) {
                    System.out.println("Error: That is a " + pieceOnTile.getColor() + " piece! It is " + currentTurnColor + "'s turn. Try again!");
                    return;
                }

                //does the piece have any valid moves?
                List<Tile> moves = pieceOnTile.getPossibleMoves(board, target);
                if (moves.isEmpty()) {
                    System.out.println("Error: That piece has no valid moves! Try again!");
                    return;
                }else{
                    allowedMoves = moves;
                }
            }
            // check validity of destination input
            else {
                //  parse input and get the target tile
                Tile targetTile = board.getTile(coords[0], coords[1]);

                if (!allowedMoves.contains(targetTile)) {
                    System.out.println("Invalid move! That tile is not highlighted. Try again!");
                    return;
                }

            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input.");
        }

        // either cache the source or execute the move
        if (target == null) return;
        if (source) {
            start = target;
            highLightTiles(allowedMoves);
        } else {
            game.executeMove(new MoveCommand(game, start, target));
            highLightTiles(List.of());
            start = null;
        }
        source = !source;
    }
}
