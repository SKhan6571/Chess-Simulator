package renderer;

import board.Board;
import com.jogamp.opengl.*;

public class JOGLRenderer implements GLEventListener {
    private Board board;
    private GL2 gl;

    void setBoard(Board board) {
        this.board = board;
    }

    void clear() { // mostly for aesthetics, and external usage
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    }

    void drawBoard() {
        // TODO: pretty much just black and white squares,
        // this never changes, so separate function
    }

    void drawPieces() {
        // TODO
        // parse the board and draw pieces
    }


    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 0);
        //TODO: shaders (shouldn't need for GL2)
        //TODO: load textures for board & pieces
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // this frees up resources, shouldn't need to do anything for our implementation
        // maybe unload textures and shaders?
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        if(board == null) return;
        drawBoard();
        drawPieces();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl.glViewport(0, 0, width, height); //2D orthographic projection, so no black magic needs to occur.
    }
}
