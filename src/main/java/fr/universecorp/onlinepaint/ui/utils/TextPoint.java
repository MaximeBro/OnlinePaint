package fr.universecorp.onlinepaint.ui.utils;

import java.awt.*;

public class TextPoint {

    private SPoint point;
    private String text;

    public TextPoint(SPoint point, String text) {
        this.point = point;
        this.text = text;
    }

    public SPoint getPoint() { return this.point; }
    public String getText() { return this.text; }
}
