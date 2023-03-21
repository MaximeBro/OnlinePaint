package fr.universecorp.onlinepaint.ui.utils;

public class SRectangle {
    private SPoint point1;
    private SPoint point2;
    private boolean filled;

    public SRectangle(SPoint point1, SPoint point2, boolean filled) {
        this.point1 = point1;
        this.point2 = point2;
        this.filled = filled;
    }

    public SPoint getPoint1() { return this.point1; }
    public SPoint getPoint2() { return this.point2; }
    public boolean isFilled() { return this.filled; }
}
