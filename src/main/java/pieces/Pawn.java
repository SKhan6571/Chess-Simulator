package pieces;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.Tile;

class Pawn extends Piece { 
    public Pawn(Color c) { super(c); } 

    //only necessary bc it extends Piece. we overwrite getmoves entirely for pawn movement rules anyways:
    @Override protected int[][] getMoveDirections() { return new int[][]{}; }
    @Override protected boolean isSliding() { return false; }

    // for the dipslay of the piece
    @Override
    public String toString() {
        //  asciii code for chesss pieces
        return this.getColor() == Color.Black ? "\u2659" : "\u265F";
    }




//CUSTOM MOVE GENERATION FOR PAWN
    @Override
    public List<Tile> getPossibleMoves(Board board, Tile start) {
        List<Tile> moves = new ArrayList<>();
        
        int rank = start.getRank();
        int file = start.getFile();
        
        // if piece is white, it increases rank to move forward; if black, decreases rank
        //bc pawns move in only one direction
        int direction = (this.getColor() == Color.White) ? 1 : -1;
        
        //checks if can move to given coordinate
        int nextRank = rank + direction;
        if (isValidCoordinate(nextRank, file)) {
            Tile target = board.getTile(nextRank, file);
            
            if (!target.isOccupied()) {
                moves.add(target);
                
                //checks ifthe pawn is in its starting position to allow a double move
                boolean isFirstMove = (this.getColor() == Color.White && rank == 1) ||
                                      (this.getColor() == Color.Black && rank == 6);
                                      
                if (isFirstMove) {
                    int doubleRank = rank + (direction * 2);
                    if (isValidCoordinate(doubleRank, file)) {
                        Tile doubleTarget = board.getTile(doubleRank, file);
                        if (!doubleTarget.isOccupied()) {
                            moves.add(doubleTarget);
                        }
                    }
                }
            }
        }

        //checks for opposite colors diagonally to capture
        int[] captureFiles = {file - 1, file + 1}; // Check left and right diagonal
        
        for (int captureFile : captureFiles) {
            if (isValidCoordinate(nextRank, captureFile)) {
                Tile target = board.getTile(nextRank, captureFile);
                
                // Must be occupied AND must be an enemy
                if (target.isOccupied() && target.getPiece().getColor() != this.getColor()) {
                    moves.add(target);
                }
            }
        }

        return moves;
    }
}