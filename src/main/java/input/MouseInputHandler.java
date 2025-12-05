package input;

import board.Tile;
import com.jogamp.opengl.awt.GLCanvas;
import game.Game;
import renderer.JOGLFacade;


public class MouseInputHandler extends InputHandler{
    GLCanvas canvas;

    public MouseInputHandler(Game game) {
        super(game);
        this.canvas = (GLCanvas) JOGLFacade.getInstance().getComponent(); // this will throw an exception if the renderer isn't a JOGLFacade'
    }

    /// x and y are in pixels
    private int[] toTile(GLCanvas canvas, int x, int y){
        float w = canvas.getWidth();
        float h = canvas.getHeight();

        float bx = (x/w) * 8;
        float by = ((h - y) / h) * 8;

        int file = (int)Math.floor(bx);
        int rank = (int)Math.floor(by);

        if (game.shouldRotateBoard()) {
            rank = 7 - rank;
            file = 7 - file;
        }
        return new int[]{rank, file};
    }

    @Override
    public void handleInput(InputEvent event) {
        if (!(event instanceof MouseClickEvent(int x, int y, boolean leftClick))) return;
        if (!leftClick){
            start = null;
            source = true;
            return;
        }
        int[] coords = toTile(canvas, x, y);
        int rank = coords[0];
        int file = coords[1];
        Tile target = game.getBoard().getTile(rank,file);
        System.out.println("Mouse click detected at:" + target.toString() + "(" + x + "," + y + ")");
        handleInputLogic(target, coords);
    }
}
