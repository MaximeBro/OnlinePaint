package fr.universecorp.onlinepaint.ui;

import fr.universecorp.onlinepaint.ui.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements IDrawable {

    private final PanelManager manager;
    private ArrayList<Object> alShapes = new ArrayList<Object>();
    private boolean pressed = false;
    private SPoint currentPoint = null;
    private SPoint basePoint = null;
    private String lastShape = "circle";
    private boolean fill = false;

    public DrawPanel(PanelManager manager) {
        this.manager = manager;

        this.setBackground(new Color(0xBEBBB1));
        this.setBorder(BorderFactory.createBevelBorder(0, Color.BLACK, Color.BLACK));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;


        for(Object shape : this.alShapes) {
            if(shape instanceof SCircle circle) {
                g2d.setColor(circle.getPoint1().getColor());
                g2d.setStroke(new BasicStroke(2));

                int x = Math.min(circle.getPoint1().x, circle.getPoint2().x);
                int y = Math.min(circle.getPoint1().y, circle.getPoint2().y);
                int radius = circle.getRadius();

                if(circle.isFilled()) { g2d.fillOval(x, y , radius, radius); }
                else { g2d.drawOval(x, y , radius, radius); }
            }


            if(shape instanceof TextPoint text) {
                SPoint point = text.getPoint();
                g2d.setColor(point.getColor() == null ? this.manager.getColor() : point.getColor());
                g2d.setFont(new Font(this.getFont().getFontName(), this.getFont().getStyle(), 22));
                g2d.drawString(text.getText(), point.x, point.y);
            }


            if(shape instanceof Straight line) {
                g2d.setStroke(new BasicStroke(2));
                SPoint point1 = line.getPoint1();
                SPoint point2 = line.getPoint2();

                if(point1 != null && point2 != null) {
                    g2d.setColor(point1.getColor());
                    g2d.drawLine(point1.x, point1.y, point2.x, point2.y);
                }
            }


            if(shape instanceof SRectangle rec) {
                g2d.setStroke(new BasicStroke(4));
                SPoint point1 = rec.getPoint1();
                SPoint point2 = rec.getPoint2();

                if(point1 != null && point2 != null) {
                    int x = Math.min(point1.x, point2.x);
                    int y = Math.min(point1.y, point2.y);
                    int w = Math.abs(point1.x - point2.x);
                    int h = Math.abs(point1.y - point2.y);

                    g2d.setColor(point1.getColor() == null ? this.manager.getColor() : point1.getColor());
                    if(rec.isFilled()) { g2d.fillRect(x, y, w, h); }
                    else { g2d.drawRect(x, y, w, h); }
                }
            }
        }

        // Objet courant
        if(this.basePoint != null && this.currentPoint != null) {
            switch (this.lastShape) {
                case "rectangle" -> {
                    g2d.setColor(this.basePoint.getColor());
                    int x = Math.min(this.basePoint.x, this.currentPoint.x);
                    int y = Math.min(this.basePoint.y, this.currentPoint.y);
                    int w = Math.abs(this.basePoint.x - this.currentPoint.x);
                    int h = Math.abs(this.basePoint.y - this.currentPoint.y);

                    if(this.manager.isShapeFilled()) { g2d.fillRect(x, y, w, h); }
                    else { g2d.drawRect(x, y, w, h); }
                }

                case "line" -> {
                    g2d.setStroke(new BasicStroke(2));
                    g2d.setColor(this.basePoint.getColor());
                    g2d.drawLine(this.basePoint.x, this.basePoint.y, this.currentPoint.x, this.currentPoint.y);
                }

                case "circle" -> {
                    g2d.setStroke(new BasicStroke(2));
                    g2d.setColor(this.basePoint.getColor());

                    int x = Math.min(this.basePoint.x, this.currentPoint.x);
                    int y = Math.min(this.basePoint.y, this.currentPoint.y);
                    int radius = Math.abs(this.basePoint.x - this.currentPoint.x);

                    if(this.manager.isShapeFilled()) { g2d.fillOval(x, y, radius, radius); }
                    else { g2d.drawOval(x, y, radius, radius); }
                }
                default -> { }
            }
        }

        this.manager.majIHM();
    }


    /*
     * MouseEvents
     * Quand le clic gauche de la souris est maintenu on dessine
     * en continu avec la couleur choisie (ou par défaut : rouge).
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.pressed = true;

        if(this.manager.getSelectedShape().equals("fill")) { this.fill = true; }
        if(this.manager.getSelectedShape().equals("empty")) { this.fill = false; }

        switch (this.manager.getSelectedShape()) {
            case "eraser" -> { // La gomme supprime absolument tout !
                this.alShapes = new ArrayList<Object>();
                this.basePoint = null;
                this.currentPoint = null;
                this.repaint();
            }

            case "circle" -> {
                this.lastShape = "circle";

                this.basePoint = new SPoint(e.getPoint(), 2);
                this.basePoint.draw(this.manager.getColor());

                this.repaint();
            }

            case "rectangle" -> {
                this.lastShape = "rectangle";

                this.basePoint = new SPoint(e.getPoint(), 2);
                this.basePoint.draw(this.manager.getColor());

                this.repaint();
            }

            case "line" -> {
                this.lastShape = "line";

                this.basePoint = new SPoint(e.getPoint(), 2);
                this.basePoint.draw(this.manager.getColor());

                this.repaint();
            }

            case "text" -> {
                this.lastShape = "text";
                String text = JOptionPane.showInputDialog(new TextPane(), "Saisissez votre texte");
                SPoint point = new SPoint(e.getPoint(), 0);
                point.draw(this.manager.getColor());

                if(text != null)
                    this.alShapes.add(new TextPoint(point, text));

                this.repaint();
            }
            default -> { }
        }
    }


    // Forme courante
    @Override
    public void mouseDragged(MouseEvent e) {
        if(this.pressed) {
            switch (this.lastShape) {
                case "rectangle", "line", "circle" -> {
                    this.currentPoint = new SPoint(e.getPoint(), 2);
                    this.currentPoint.draw(this.manager.getColor());
                    this.repaint();
                }

                default -> { }
            }
        }



        this.repaint();
    }

    // Forme approuvée
    @Override
    public void mouseReleased(MouseEvent e) {
        if(this.pressed) {
            switch (this.lastShape) {
                case "rectangle" -> {
                    this.currentPoint = new SPoint(e.getPoint(), 2);
                    this.currentPoint.draw(this.manager.getColor());
                    if(this.manager.isShapeFilled())
                        this.alShapes.add(new SRectangle(this.basePoint, this.currentPoint, true));
                    else
                        this.alShapes.add(new SRectangle(this.basePoint, this.currentPoint, false));

                    this.basePoint = null;
                    this.currentPoint = null;

                    this.repaint();
                }

                case "line" -> {
                    this.currentPoint = new SPoint(e.getPoint(), 2);
                    this.currentPoint.draw(this.manager.getColor());
                    this.alShapes.add(new Straight(this.basePoint, this.currentPoint));

                    this.basePoint = null;
                    this.currentPoint = null;

                    this.repaint();
                }

                case "circle" -> {
                    this.currentPoint = new SPoint(e.getPoint(), 2);
                    this.currentPoint.draw(this.manager.getColor());

                    this.alShapes.add(new SCircle(this.basePoint, this.currentPoint, this.manager.isShapeFilled()));

                    this.basePoint = null;
                    this.currentPoint = null;

                    this.repaint();
                }

                default -> { }
            }

        }

        this.pressed = false;
    }


    public void removeLast() {
        int size = this.alShapes.size();
        if(size > 0) { this.alShapes.remove(size-1); }
        this.repaint();
    }

    public ArrayList<Object> getLst() { return this.alShapes; }

    private class TextPane extends JPanel {
        private JTextField txtField;

        public TextPane() {
            this.txtField = new JTextField(10);
        }

        public String getText() { return this.txtField.getText(); }
    }
}
