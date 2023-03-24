package fr.maximebrochard.onlinepaint.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ShapesPanel extends JPanel {

    private JButton circle;
    private JButton rectangle;
    private JButton line;
    private JButton text;
    private JButton fill;
    private JButton undo;
    private JButton eraser;

    private String shape = "circle";
    private boolean isFilled = true;
    private final PanelManager manager;

    public ShapesPanel(PanelManager manager, Dimension size) {
        this.manager = manager;

        this.setLayout(new GridLayout(1, 7));
        this.setPreferredSize(new Dimension(size.width, 60));

        try {
            this.circle = new JButton("Cercle", new ImageIcon(ImageIO.read(getClass().getResource("/images/circle.png"))));
            this.rectangle = new JButton("Rectangle", new ImageIcon(ImageIO.read(getClass().getResource("/images/rectangle.png"))));
            this.line = new JButton("Droite", new ImageIcon(ImageIO.read(getClass().getResource("/images/line.png"))));
            this.text = new JButton("Texte", new ImageIcon(ImageIO.read(getClass().getResource("/images/text.png"))));
            this.eraser = new JButton("Gomme", new ImageIcon(ImageIO.read(getClass().getResource("/images/eraser.png"))));
        } catch (Exception e) { e.printStackTrace(); }


        this.undo = new JButton("Undo");
        this.fill = new JButton("Plein");
        this.isFilled = true;

        this.circle.setFocusPainted(false);
        this.rectangle.setFocusPainted(false);
        this.line.setFocusPainted(false);
        this.text.setFocusPainted(false);
        this.fill.setFocusPainted(false);
        this.eraser.setFocusPainted(false);

        this.circle.addActionListener(e -> { this.shape = "circle"; });
        this.rectangle.addActionListener(e -> { this.shape = "rectangle"; });
        this.line.addActionListener(e -> { this.shape = "line"; });
        this.text.addActionListener(e -> { this.shape = "text"; });
        this.undo.addActionListener(e -> { this.manager.removeLast(); });

        this.fill.addActionListener(e -> {
            if(this.isFilled) {
                this.fill.setText("Vide");
                this.isFilled = false;
            } else {
                this.fill.setText("Plein");
                this.isFilled = true;
            }

        });

        this.eraser.addActionListener(e -> { this.shape = "eraser"; });

        this.add(this.circle);
        this.add(this.rectangle);
        this.add(this.line);
        this.add(this.text);
        this.add(this.fill);
        this.add(this.undo);
        this.add(this.eraser);
    }

    public String getSelectedShape() { return this.shape; }
    public boolean isFilled() { return this.isFilled; }
}
