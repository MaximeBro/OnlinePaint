package fr.universecorp.onlinepaint.ui.utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface IDrawable extends MouseListener, MouseMotionListener {

    @Override
    void mousePressed(MouseEvent e);

    @Override
    void mouseDragged(MouseEvent e);

    @Override
    void mouseReleased(MouseEvent e);

    @Override
    default void mouseEntered(MouseEvent e) {}

    @Override
    default void mouseExited(MouseEvent e) {}

    @Override
    default void mouseMoved(MouseEvent e) {}

    @Override
    default void mouseClicked(MouseEvent e) {}
}
