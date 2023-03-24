package fr.maximebrochard.onlinepaint.ui.utils;

public class Straight {

    private SPoint point1;
    private SPoint point2;

    public Straight(SPoint point1, SPoint point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public void setPoint1(SPoint point) { this.point1 = point; }
    public void setPoint2(SPoint point) { this.point2 = point; }

    public SPoint getPoint1() { return this.point1; }
    public SPoint getPoint2() { return this.point2; }


    public boolean equals(Straight line) {

        if( this.getPoint1().x == line.getPoint1().x &&
            this.getPoint1().y == line.getPoint1().y &&
            this.getPoint2().x == line.getPoint2().x &&
            this.getPoint2().y == line.getPoint2().y
        )   return true;


            return false;
    }
}
