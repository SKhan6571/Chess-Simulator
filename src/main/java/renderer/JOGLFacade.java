package renderer;

import board.Board;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

public class JOGLFacade implements Renderer {
    private final GLCanvas canvas;
    private final JOGLRenderer impl;

    public JOGLFacade() {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        this.canvas = new GLCanvas(caps);

        this.impl = new JOGLRenderer();
        canvas.addGLEventListener(impl);

        JFrame frame = new JFrame("Chess");
        frame.setSize(800,800); // just an arbitrary size
        frame.add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void render() {
        canvas.display();
    }

    @Override
    public void clear() {
        impl.clear();
    }

    @Override
    public void setBoard(Board board) {
        impl.setBoard(board);
    }

    @Override
    public java.awt.Component getComponent() {
        return canvas;
    }
}
