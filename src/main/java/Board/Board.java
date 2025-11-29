package Board;


public abstract class Board {
    private Tile[][] tiles;
    private int size = 7;

    Board(int size) {
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
//                tiles[i][j] = TileFactory.create(i,j);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Tile getTile(int x, int y){
        return tiles[x][y];
    }

}
