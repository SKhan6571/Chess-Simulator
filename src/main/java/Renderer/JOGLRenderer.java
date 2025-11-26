package Renderer;

import Board.Board;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.*;

public class JOGLRenderer implements Renderer{
    //OpenGL renderer, OGL is state-based, so this might be a bit of a mess.
    private Board board;
    private boolean initialized = false;

    //OGL stuff
    private GL4 gl = null;
    private GLAutoDrawable drawable = null;
    private GLProfile profile = null;
    private GLCapabilities capabilities = null;
    private GLCanvas canvas = null;

    public JOGLRenderer(GLCanvas canvas){
        this.init();
    }

    private void init(){
        if(initialized) return;
        this.drawable = (GLAutoDrawable) this;
        this.gl = drawable.getGL().getGL4();
        this.profile = GLProfile.get(GLProfile.GL4);
        this.capabilities = new GLCapabilities(profile);
        this.canvas =  new GLCanvas(capabilities);
        initialized = true;
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {

    }


    @Override
    public void clear() {
        gl.glClear( GL4.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void setBoard(Board board){

    }
}