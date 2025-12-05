package game;

import board.Tile;
import pieces.Piece;

public class MoveCommand implements Command {
    private final Tile startTile;
    private final Tile endTile;
    private final Piece movedPiece;
    private final Piece capturedPiece;
    private final Game game; // Reference to trigger updates

    public MoveCommand(Game game, Tile start, Tile end) {
        this.game = game;
        this.startTile = start;
        this.endTile = end;
        this.movedPiece = start.getPiece();
        this.capturedPiece = end.getPiece(); // Save for undo
    }

    @Override
    public void execute() {
        // Execute the move
        endTile.setPiece(movedPiece);
        startTile.setPiece(null);

        // update piece tracker
        if(endTile.getPiece() != null){
            game.removePiece(endTile);
        }
        game.swapPieceTile(startTile, endTile);

        // Notify observers that state changed
        game.getBoard().notifyObservers();
    }

    @Override
    public void undo() {
        // Reverse the move
        startTile.setPiece(movedPiece);
        endTile.setPiece(capturedPiece); // Restore captured piece (or null)

        // undo piece tracker updates
        game.swapPieceTile(endTile, startTile);
        if(capturedPiece != null){
            game.addPiece(endTile);
        }

        // Notify observers that state changed
        game.getBoard().notifyObservers();
    }
}