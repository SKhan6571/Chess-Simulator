package Renderer;

import Board.Board;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

public class JOGLRenderer implements Renderer{ // this also needs to implement GLEventListener, which means that most of this is broken right now...
    //OpenGL renderer, OGL is state-based, so this might be a bit of a mess.
    private Board board;
    private boolean initialized = false;

    //OGL stuff
    private GL4 gl = null;
    private GLAutoDrawable drawable = null;
    private GLProfile profile = null;
    private GLCapabilities capabilities = null;
    private GLCanvas canvas = null;

    public JOGLRenderer() {
        this.init();
    }

    private void init() {
        if (initialized) return;
        this.drawable = (GLAutoDrawable) this; // this is probably broken...
        this.gl = drawable.getGL().getGL4();
        this.profile = GLProfile.get(GLProfile.GL4);
        this.capabilities = new GLCapabilities(profile);
        this.canvas = new GLCanvas(capabilities);
        initialized = true;
    }

    @Override
    public void render() {
        update();
        display();
    }

    @Override
    public void display() {
        // display to screen, (just a buffer swap?)
    }

    @Override
    public void update() {
        // prepare the next frame
        clear();
    }


    @Override
    public void clear() {
        // clears the next buffer
        gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void setBoard(Board board) {

    }
}