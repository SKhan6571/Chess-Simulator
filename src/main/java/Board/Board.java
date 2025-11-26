package Board;


public abstract class Board {
    private Tile[][] tiles;

    Board(int size) {
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                tiles[i][j] = TileFactory.create(i,j);
            }
        }
    }


}
