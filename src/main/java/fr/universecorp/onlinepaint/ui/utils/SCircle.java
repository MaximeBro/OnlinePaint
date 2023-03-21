package fr.universecorp.onlinepaint.ui.utils;

import java.awt.*;

public class SCircle {

    private SPoint point1;
    private SPoint point2;
    private int radius;
    private boolean filled;

    public SCircle(SPoint point1, SPoint point2, boolean filled) {
        this.point1 = point1;
        this.point2 = point2;
        this.radius = Math.abs(this.point1.x - this.point2.x);
        this.filled = filled;
    }

    public int getRadius() { return this.radius; }
    public SPoint getPoint1() { return this.point1; }
    public SPoint getPoint2() { return this.point2; }
    public boolean isFilled() { return this.filled; }
}
