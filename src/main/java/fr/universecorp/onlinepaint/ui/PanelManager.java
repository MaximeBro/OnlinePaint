package fr.universecorp.onlinepaint.ui;

import fr.universecorp.onlinepaint.network.Client;
import fr.universecorp.onlinepaint.ui.utils.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelManager extends JFrame {

    private DrawPanel panelDessin;
    private ColorChooserPanel panelCouleur;
    private ShapesPanel panelFormes;
    private final Client client;


    public PanelManager(Client client) {
        this.client = client;

        String name = JOptionPane.showInputDialog("Saisir votre nom d'utilisateur :");
        while(name == null || name.equalsIgnoreCase("")) {
            name = JOptionPane.showInputDialog("Saisir votre nom d'utilisateur :");
        }

        this.client.setName(name);
        this.setTitle("OnlinePaint - " + name);
        this.setSize(1080, 720);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - 1080/2,
                         (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 - 720/2);

        this.panelDessin = new DrawPanel(this);
        this.panelCouleur = new ColorChooserPanel(this.getSize());
        this.panelFormes = new ShapesPanel(this, this.getSize());

        this.add(this.panelDessin, BorderLayout.CENTER);
        this.add(this.panelFormes, BorderLayout.NORTH);
        this.add(this.panelCouleur, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public Color getColor() { return this.panelCouleur.getColor(); }
    public String getSelectedShape() { return this.panelFormes.getSelectedShape(); }
    public boolean isShapeFilled() { return this.panelFormes.isFilled(); }
    public void removeLast() { this.panelDessin.removeLast(); }

    public void majIHM(String action) {
        this.client.action(action);
    }

    public ArrayList<String> getData(ArrayList<Object> lst) {
        ArrayList<String> alRet = new ArrayList<String>();

        for(Object shape : lst) {
            if(shape instanceof SCircle circle) {
                alRet.add("circle," + circle.getPoint1().x + "," + circle.getPoint1().y + "," +
                                      circle.getPoint2().x + "," + circle.getPoint2().y + "," + circle.getRadius() + "," +
                                      circle.isFilled()    + "," + this.getColorEq(circle.getPoint1().getColor()));
            }

            if(shape instanceof TextPoint text) {
                alRet.add("text," + text.getPoint().x + "," + text.getPoint().y + "," +
                                    text.getText()    + "," + this.getColorEq(text.getPoint().getColor()));
            }

            if(shape instanceof Straight line) {
                alRet.add("line," + line.getPoint1().x + "," + line.getPoint1().y + "," +
                                    line.getPoint2().x + "," + line.getPoint2().y + "," +
                                    this.getColorEq(line.getPoint1().getColor()));
            }

            if(shape instanceof SRectangle rec) {
                alRet.add("rectangle," + rec.getPoint1().x + "," + rec.getPoint1().y + "," +
                                         rec.getPoint2().x + "," + rec.getPoint2().y + "," +
                                         rec.isFilled()    + "," + this.getColorEq(rec.getPoint1().getColor()));
            }
        }

        return alRet;
    }

    public void setData(String data) {

        Object objet = null;

        String[] donnees = data.split(",");
        if(donnees[1].equals("add")) {
            switch (donnees[2]) {
                case "circle" -> {
                    SCircle circle = new SCircle(
                            new SPoint(new Point(Integer.parseInt(donnees[2]), Integer.parseInt(donnees[3])), 0),
                            new SPoint(new Point(Integer.parseInt(donnees[4]), Integer.parseInt(donnees[5])), 0),
                            Boolean.parseBoolean(donnees[7]));

                    circle.getPoint1().draw(this.getColorFromString(donnees[8]));
                }

                case "text" -> {

                }

                case "line" -> {

                }

                case "rectangle" -> {

                }

                default ->  { }
            }
        }


        this.panelDessin.addItem(objet);
    }

    public Color getColorFromString(String color) {

        return switch (color) {
            case "red"    -> Color.RED;
            case "blue"   -> Color.BLUE;
            case "yellow" -> Color.YELLOW;
            case "green"  -> Color.GREEN;
            case "black"  -> Color.BLACK;
            case "white"  -> Color.WHITE;

            default -> Color.RED;
        };
    }

    public String getColorEq(Color color) {

        if(color.equals(Color.RED)) { return "red"; }
        if(color.equals(Color.BLUE)) { return "blue"; }
        if(color.equals(Color.YELLOW)) { return "yellow"; }
        if(color.equals(Color.GREEN)) { return "green"; }
        if(color.equals(Color.BLACK)) { return "black"; }
        if(color.equals(Color.WHITE)) { return "white"; }

        return "";
    }
}
