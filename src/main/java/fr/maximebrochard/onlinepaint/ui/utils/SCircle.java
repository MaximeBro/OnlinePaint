package fr.maximebrochard.onlinepaint.ui.utils;

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


    public boolean equals(SCircle circle) {

        if(this.isFilled() == circle.isFilled() &&
                this.getPoint1().x == circle.getPoint1().x &&
                this.getPoint1().y == circle.getPoint1().y &&
                this.getPoint2().x == circle.getPoint2().x &&
                this.getPoint2().y == circle.getPoint2().y &&
                this.getRadius() == circle.getRadius()
        )       return true;

        return false;
    }
}
