package fr.maximebrochard.onlinepaint.ui.utils;

import java.awt.*;

public class SPoint extends Point {

    private Color drawn;
    private int radius;

    public SPoint(Point point, int radius) {
        super(point);
        this.drawn = Color.RED;
        this.radius = radius;
    }

    public void draw(Color color) { this.drawn = color; }
    public Color getColor() { return this.drawn; }
    public int getRadius() { return this.radius; }
}
