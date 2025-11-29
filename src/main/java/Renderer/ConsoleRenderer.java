package Renderer;

import Board.Board;
import Board.Tile;

public class ConsoleRenderer implements Renderer{
    // Honestly, not sure if we can even make inputs to this that don't suck...
    private Board board;

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public void render() {
//        update();
        display();
    }

    @Override
    public void display() {
        if (board == null) return;
        for (int rank = board.getSize(); rank > 0; rank--) {
            for (int file = 0; file <= board.getSize(); file++) {
                Tile tile = board.getTile(rank,file);
                System.out.print(tile.toString() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void update() {
        //does nothing in this case
    }

    @Override
    public void clear() {

    }
}
