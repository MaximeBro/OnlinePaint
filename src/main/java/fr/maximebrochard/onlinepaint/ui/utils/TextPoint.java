package fr.maximebrochard.onlinepaint.ui.utils;

public class TextPoint {

    private SPoint point;
    private String text;

    public TextPoint(SPoint point, String text) {
        this.point = point;
        this.text = text;
    }

    public SPoint getPoint() { return this.point; }
    public String getText() { return this.text; }


    public boolean equals(TextPoint text) {

        if(this.getText().equals(text.getText()) &&
                this.getPoint().x == text.getPoint().x &&
                this.getPoint().y == text.getPoint().y
        )   return true;

        return false;
    }
}
