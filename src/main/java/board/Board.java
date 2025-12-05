package board;
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

    public void highlightTiles(List<Tile> tiles){
        for (GameObserver observer : observers) {
            observer.highlightTiles(tiles);
            observer.onBoardUpdate(this);
        }
    }
}