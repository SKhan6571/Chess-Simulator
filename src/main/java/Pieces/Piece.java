package pieces;
import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.Tile;


//////// TEMPLATE METHOD PATTERN USED HERE FOR ALL PIECES!!!!!!!!!!
public abstract class Piece {
    protected final Color color;
    public Piece(Color color) { this.color = color; }
    public Color getColor() { return color; }
    
    @Override //for the display of the pieces
    public abstract String toString(); 



    //abstract attributes to be implemented by child classes
    // NOTE: You only need to provide positive directions
    protected abstract int[][] getMoveDirections();
    protected abstract boolean isSliding(); // KNIGHT KING AND PAWN WILL RETURN FALSE (bc they only move 1 space at a time)
    //then the details of movement direction and sliding is used to get all th epossible moves for the selected piece
    //pretty sure ill have to make pawn override this method entirely to account for its special movement rules
    public List<Tile> getPossibleMoves(Board board, Tile start) {
        List<Tile> moves = new ArrayList<>();
        
        // which direction the piece can move.
        int[][] directions = getMoveDirections(); 
        // Parent uses these to generate possible moves for each piece

        for (int[] baseDir : directions) {
            //ensures symmetries are considered because chess pieces can move whatever way.
            //so a rook moving (1,0) can also move (-1,0),(0,1),(0,-1)
            List<int[]> symmetries = getSymmetries(baseDir);

            for (int[] dir : symmetries) {
                int nextRank = start.getRank() + dir[0];
                int nextFile = start.getFile() + dir[1];
                
                while (isValidCoordinate(nextRank, nextFile)) {
                    Tile target = board.getTile(nextRank, nextFile);
                    if (!target.isOccupied()) {
                        moves.add(target);
                    } else {
                        if (target.getPiece().getColor() != this.getColor()) {
                            moves.add(target);
                        }
                        break; // Blocked
                    }
                    // break if piece doesnt slide (means it only moves 1 space at a time)
                    if (!isSliding()) break; 

                    nextRank += dir[0];
                    nextFile += dir[1];
                }
            }
        }
        return moves;
    }




    protected boolean isValidCoordinate(int nextRank, int nextFile) {
        return (nextRank >= 0 && nextRank < 8) && (nextFile >= 0 && nextFile < 8);
    }






    // takes a direction like {1, 2} or {0, 1} and returns all the mirrored versions
    private List<int[]> getSymmetries(int[] dir) {
        List<int[]> syms = new ArrayList<>();
        
        // if one part is 0, flipping it doesn't change anything,
        // so we only need one option
        // otherwise we can just flip its sign to get the symmetry
        int[] xSigns = (dir[0] == 0) ? new int[]{1} : new int[]{1, -1};
        int[] ySigns = (dir[1] == 0) ? new int[]{1} : new int[]{1, -1};

        // try all combinations of flipping x and flipping y
        for (int x : xSigns) {
            for (int y : ySigns) {
                // multiplying the original direction by the chosen signs flips it and makes the symmetry
                syms.add(new int[] { dir[0] * x, dir[1] * y });
            }
        }
        
        return syms;
    }
}