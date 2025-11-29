package Renderer;

import Board.Board;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

public class JOGLRendererFacade implements Renderer{
    private final GLCanvas canvas;
    private final JOGLRendererImpl impl;

    public JOGLRendererFacade() {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        this.canvas = new GLCanvas(capabilities);

        this.impl = new JOGLRendererImpl();
        this.canvas.addGLEventListener(impl);

        JFrame frame = new JFrame("Chess");
        frame.setSize(600,600);
        frame.add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void display() {
        impl.display(canvas);
    }

    @Override
    public void update() {
        impl.update(canvas);
    }

    @Override
    public void render() {
        update();
        display();
    }

    @Override
    public void clear() {
        impl.clear(canvas);
    }

    @Override
    public void setBoard(Board board) {
        impl.setBoard(board);
    }
}
