package board;

import pieces.Color;
import pieces.Piece;
import pieces.PieceFactory;
import renderer.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Tile[][] tiles;
    // Board has a list of observers
    private final List<GameObserver> observers = new ArrayList<>();

    public Board() {
        int size = 8;
        tiles = new Tile[size][size];

        for (int rank = 0; rank < size; rank++) {
            for (int file = 0; file < size; file++) {
                // Create a blank tile at this coordinates
                tiles[rank][file] = new Tile(rank, file);

                //place the pieces onto the tile - some will be null if a piece doesn't go there
                tiles[rank][file].setPiece(PieceFactory.createPiece(rank, file));


                /////UTILIZES FACTORY PATTERN TO CREATE PIECES based on board pos!!!!!!!!!
            }
        }
    }

    // Check and checkmate logic

    public boolean isCheckmate(Color kingColor) {
        if (!isInCheck(kingColor)) {
            return false;
        }
        // If in check, can we make ANY move that escapes?
        return !hasLegalMoves(kingColor);
    }

    // Also useful for Stalemate (not in check, but no legal moves)
    public boolean hasLegalMoves(Color color) {
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                Tile tile = tiles[r][f];
                if (tile.isOccupied() && tile.getPiece().getColor() == color) {
                    List<Tile> legal = tile.getPiece().getLegalMoves(this, tile);
                    if (!legal.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isInCheck(Color kingColor) {
        Tile kingTile = findKing(kingColor);
        // Find opposite color
        Color enemyColor = (kingColor == Color.White) ? Color.Black : Color.White;
        return isSquareUnderAttack(kingTile.getRank(), kingTile.getFile(), enemyColor);
    }

    public boolean isSquareUnderAttack(int rank, int file, Color enemyColor) {
        // Iterate over all tiles to find enemy pieces
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                Tile tile = tiles[r][f];
                if (tile.isOccupied() && tile.getPiece().getColor() == enemyColor) {
                    List<Tile> threats = tile.getPiece().getThreatenedTiles(this, tile);
                    // Check if the target coordinate is in the threat list
                    for (Tile threat : threats) {
                        if (threat.getRank() == rank && threat.getFile() == file) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private Tile findKing(Color color) {
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                if (tiles[r][f].isOccupied()) {
                    Piece p = tiles[r][f].getPiece();
                    if (p.isKing() && p.getColor() == color) {
                        return tiles[r][f];
                    }
                }
            }
        }
        throw new RuntimeException("King not found on board!");
    }

    /// /// UTILIZES OBSERVER PATTERN TO REGISTER AND NOTIFY OBSERVERS!!!

    // Observer Pattern: Registration
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    // Observer Pattern: Notification
    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.onBoardUpdate(this);
        }
    }

    public Tile getTile(int rank, int file) {
        return tiles[rank][file];
    }

    // We no longer need updateBoard() method. An observer prints the board when it changes state.
}