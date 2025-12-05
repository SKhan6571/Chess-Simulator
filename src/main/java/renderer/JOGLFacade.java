package renderer;

import board.Tile;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import game.Game;

import javax.swing.*;
import java.util.List;

public class JOGLFacade implements Renderer {
    private final GLCanvas canvas;
    private final JOGLRenderer impl;
    private final FPSAnimator animator; //used to keep the game displayed during some edge cases

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

        animator = new FPSAnimator(canvas, 60);
        animator.start();
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
    public void setGame(Game game) {
        impl.setGame(game);
    }

    @Override
    public void setHighlightedTiles(List<Tile> highlightedTiles) {
        impl.setHighlightedTiles(highlightedTiles);
    }

    @Override
    public java.awt.Component getComponent() {
        return canvas;
    }
}
