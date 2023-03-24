package fr.maximebrochard.onlinepaint.ui.utils;

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


    public boolean equals(SRectangle rec) {

        if(this.isFilled() == rec.isFilled() &&
                this.getPoint1().x == rec.getPoint1().x &&
                this.getPoint1().y == rec.getPoint1().y &&
                this.getPoint2().x == rec.getPoint2().x &&
                this.getPoint2().y == rec.getPoint2().y
        )   return true;

            return false;
    }
}
