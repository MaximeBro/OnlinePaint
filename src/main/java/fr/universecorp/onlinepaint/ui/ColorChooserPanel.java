package fr.universecorp.onlinepaint.ui;

import javax.swing.*;
import java.awt.*;

public class ColorChooserPanel extends JPanel {

    private JButton redBtn;
    private JButton blueBtn;
    private JButton yellowBtn;
    private JButton greenBtn;
    private JButton blackBtn;
    private JButton whiteBtn;

    private Color color = Color.RED;

    public ColorChooserPanel(Dimension size) {

        this.setLayout(new GridLayout(1, 6));
        this.setPreferredSize(new Dimension(size.width, 60));

        this.redBtn = new JButton();
        this.blueBtn = new JButton();
        this.yellowBtn = new JButton();
        this.greenBtn = new JButton();
        this.blackBtn = new JButton();
        this.whiteBtn = new JButton();

        this.redBtn.setBackground(Color.RED);
        this.blueBtn.setBackground(Color.BLUE);
        this.yellowBtn.setBackground(Color.YELLOW);
        this.greenBtn.setBackground(Color.GREEN);
        this.blackBtn.setBackground(Color.BLACK);
        this.whiteBtn.setBackground(Color.WHITE);

        this.redBtn.addActionListener(e -> { this.color = Color.RED; });
        this.blueBtn.addActionListener(e -> { this.color = Color.BLUE; });
        this.yellowBtn.addActionListener(e -> { this.color = Color.YELLOW; });
        this.greenBtn.addActionListener(e -> { this.color = Color.GREEN; });
        this.blackBtn.addActionListener(e -> { this.color = Color.BLACK; });
        this.whiteBtn.addActionListener(e -> { this.color = Color.WHITE; });



        this.add(this.redBtn);
        this.add(this.blueBtn);
        this.add(this.yellowBtn);
        this.add(this.greenBtn);
        this.add(this.blackBtn);
        this.add(this.whiteBtn);
    }

    public Color getColor() { return this.color; }
}
