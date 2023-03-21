package fr.universecorp.onlinepaint.ui.utils;

import fr.universecorp.onlinepaint.ui.ShapesPanel;

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
}
