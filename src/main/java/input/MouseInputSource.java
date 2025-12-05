package input;

import renderer.JOGLFacade;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInputSource{

    public MouseInputSource(InputListener listener) {
        Component component = JOGLFacade.getInstance().getComponent();

        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                listener.onInput(new MouseClickEvent(e.getX(), e.getY(),e.getButton() == MouseEvent.BUTTON1));
            }
        });
    }
}
