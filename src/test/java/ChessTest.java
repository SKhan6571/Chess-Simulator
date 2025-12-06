import board.Board;
import board.Tile;
import org.junit.jupiter.api.Test;
import pieces.Color;
import pieces.Piece;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChessTest {

    /**
     * Test 1: Board Initialization
     * Verifies that the board initializes with pieces in the correct starting positions.
     * We check the corners for Rooks and ensure the correct color and type are present.
     */
    @Test
    public void testBoardInitialization() {
        Board board = new Board();

        // Check White Rook at a1 (0,0)
        Tile whiteRookTile = board.getTile(0, 0);
        assertTrue(whiteRookTile.isOccupied(), "Tile a1 should be occupied");
        assertEquals(Color.White, whiteRookTile.getPiece().getColor(), "Piece at a1 should be White");
        assertEquals("Rook", whiteRookTile.getPiece().getClass().getSimpleName(), "Piece at a1 should be a Rook");

        // Check Black Rook at h8 (7,7)
        Tile blackRookTile = board.getTile(7, 7);
        assertTrue(blackRookTile.isOccupied(), "Tile h8 should be occupied");
        assertEquals(Color.Black, blackRookTile.getPiece().getColor(), "Piece at h8 should be Black");
        assertEquals("Rook", whiteRookTile.getPiece().getClass().getSimpleName(), "Piece at h8 should be a Rook");
    }

    /**
     * Test 2: Pawn Opening Move Logic
     * Verifies that a pawn on its starting rank (Rank 1 for White) can move
     * either 1 square or 2 squares forward.
     */
    @Test
    public void testWhitePawnOpeningOptions() {
        Board board = new Board();
        // Get the White Pawn at e2 (Rank 1, File 4)
        Tile pawnTile = board.getTile(1, 4);
        Piece pawn = pawnTile.getPiece();

        List<Tile> moves = pawn.getLegalMoves(board, pawnTile);

        boolean canMoveOneSquare = false;
        boolean canMoveTwoSquares = false;

        for (Tile t : moves) {
            if (t.getRank() == 2 && t.getFile() == 4) canMoveOneSquare = true;
            if (t.getRank() == 3 && t.getFile() == 4) canMoveTwoSquares = true;
        }

        assertTrue(canMoveOneSquare, "Pawn should be able to move 1 square forward (e3)");
        assertTrue(canMoveTwoSquares, "Pawn should be able to move 2 squares forward (e4) on first move");
    }

    /**
     * Test 3: Rook Movement Blocked
     * Verifies that sliding pieces (like the Rook) cannot move through friendly pieces.
     * At the start of the game, the Rook at a1 is blocked by pawns and the knight.
     */
    @Test
    public void testRookBlockedAtStart() {
        Board board = new Board();
        // White Rook at a1 (0,0)
        Tile rookTile = board.getTile(0, 0);
        Piece rook = rookTile.getPiece();

        List<Tile> moves = rook.getLegalMoves(board, rookTile);

        assertTrue(moves.isEmpty(), "Rook should have no legal moves at the start of the game (blocked by friends)");
    }

    /**
     * Test 4: Knight Jumping Logic
     * Verifies that Knights can jump over other pieces.
     * The Knight at b1 (0,1) is surrounded by friendly pieces but should still be able to jump to a3 or c3.
     */
    @Test
    public void testKnightJumpAbility() {
        Board board = new Board();
        // White Knight at b1 (0,1)
        Tile knightTile = board.getTile(0, 1);
        Piece knight = knightTile.getPiece();

        List<Tile> moves = knight.getLegalMoves(board, knightTile);

        assertFalse(moves.isEmpty(), "Knight should have legal moves despite being surrounded");

        boolean foundTarget = false;
        // Looking for jump to c3 (Rank 2, File 2)
        for (Tile t : moves) {
            if (t.getRank() == 2 && t.getFile() == 2) {
                foundTarget = true;
                break;
            }
        }
        assertTrue(foundTarget, "Knight should be able to jump to c3");
    }

    /**
     * Test 5: Check Detection Logic
     * Manually sets up a scenario where the King is threatened by an enemy Rook
     * and asserts that `isInCheck()` returns true.
     */
    @Test
    public void testCheckDetection() {
        Board board = new Board();

        // 1. Clear the board to set up a specific scenario
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                board.getTile(r, c).setPiece(null);
            }
        }

        // 2. Obtain piece instances (using a temp board since constructors are package-private)
        Board tempBoard = new Board();
        Piece whiteKing = tempBoard.getTile(0, 4).getPiece(); // King from e1
        Piece blackRook = tempBoard.getTile(7, 0).getPiece(); // Rook from a8

        // 3. Place pieces: King at d4 (3,3), Rook at h4 (3,7) -> Same rank, clear path
        board.getTile(3, 3).setPiece(whiteKing);
        board.getTile(3, 7).setPiece(blackRook);

        // 4. Verify that White is in check
        assertTrue(board.isInCheck(Color.White), "White King at d4 should be in check from Black Rook at h4");

        // 5. Move Rook to h5 (3,7 -> 4,7) to remove the threat
        board.getTile(3, 7).setPiece(null);
        board.getTile(4, 7).setPiece(blackRook);

        // 6. Verify White is no longer in check
        assertFalse(board.isInCheck(Color.White), "White King should no longer be in check");
    }
}