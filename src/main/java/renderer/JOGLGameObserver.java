package renderer;

import board.Board;
import game.Game;

// immediately renders the board when its state changes (separate from the animator)
public class JOGLGameObserver implements GameObserver{
    Renderer renderer;

    public JOGLGameObserver(Game game) {
        this(game,new JOGLFacade());
    }

    public JOGLGameObserver(Game game,Renderer renderer) {
        this.renderer = renderer;
        renderer.setGame(game);
    }


    @Override
    public void onBoardUpdate(Board board) {
        renderer.render();
    }
}
