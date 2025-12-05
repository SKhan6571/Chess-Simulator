package renderer;

import board.Board;
import game.Game;

// immediately renders the board when its state changes (separate from the animator)
public class JOGLGameObserver extends GameObserver {
    Renderer renderer;

    public JOGLGameObserver(Game game) {
        this(game, JOGLFacade.getInstance());
    }

    public JOGLGameObserver(Game game, Renderer renderer) {
        this.renderer = renderer;
        renderer.setGame(game);
    }


    @Override
    public void onBoardUpdate(Board board) {
        renderer.setHighlightedTiles(highlightedTiles);
        renderer.render();
    }
}
