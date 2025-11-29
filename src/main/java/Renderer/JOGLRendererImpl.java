package Renderer;

import Board.Board;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

public class JOGLRendererImpl  implements GLEventListener{
    //OpenGL renderer, OGL is state-based, so this might be a bit of a mess.
    private Board board;
    private boolean initialized = false;

    //OGL stuff
    private GL2 gl = null;
    private GLAutoDrawable drawable = null;
    private GLProfile profile = null;
    private GLCapabilities capabilities = null;
    private GLCanvas canvas = null;


    public void init(GLAutoDrawable drawable) {
        if (initialized) return;
        gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        clear(drawable);
        initialized = false;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        // display to screen, (just a buffer swap?)
        drawable.swapBuffers();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl.glViewport(0, 0, width, height);
    }

    public void update(GLAutoDrawable drawable) {
        // prepare the next frame
        clear(drawable);
        //TODO: draw the board and pieces here

    }

    public void clear(GLAutoDrawable drawable) {
        // clears the next buffer
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}