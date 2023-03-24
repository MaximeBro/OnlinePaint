package fr.maximebrochard.onlinepaint.ui;

import fr.maximebrochard.onlinepaint.network.DataStorage;
import fr.maximebrochard.onlinepaint.network.Multicast;
import fr.maximebrochard.onlinepaint.ui.utils.*;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class PanelManager extends JFrame {

    private DrawPanel panelDessin;
    private ColorChooserPanel panelCouleur;
    private ShapesPanel panelFormes;
    private String name;
    private Multicast multicast;
    private String numero;


    public PanelManager() throws IOException {

        this.name = JOptionPane.showInputDialog("Saisir votre nom d'utilisateur :");
        while(this.name == null || this.name.equalsIgnoreCase("")) {
            this.name = JOptionPane.showInputDialog("Saisir votre nom d'utilisateur :");
        }

        InetAddress groupeIp = InetAddress.getByName("230.125.10.15");
        int port = 2009;

        this.numero = ++DataStorage.numero + "";
        this.multicast = new Multicast(this, groupeIp, port, this.name);
        this.multicast.getEmetteur().emettre("name," + name + "," + this.numero);

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

    public void majIHM(String action) throws IOException {
        this.multicast.getEmetteur().emettre("data," + action);
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

        if(data.contains("supprimer") && data.contains(this.name) && data.contains(this.numero)) {
            this.dispose(); // Si un client du même nom existe on ferme le programme !
        } // Message envoyé automatiquement au groupe si un autre client avec le même nom existe déjà !

        Object objet = null;
        boolean suppression = false;

        String[] donnees = data.split(",");

        if(donnees[0].equals("add") || donnees[0].equals("remove")) {
            switch (donnees[1]) {
                case "circle" -> {
                    SCircle circle = new SCircle(
                            new SPoint(new Point(Integer.parseInt(donnees[2]), Integer.parseInt(donnees[3])), 0),
                            new SPoint(new Point(Integer.parseInt(donnees[4]), Integer.parseInt(donnees[5])), 0),
                            Boolean.parseBoolean(donnees[7]));

                    circle.getPoint1().draw(this.getColorFromString(donnees[8]));
                    objet = circle;
                }

                case "text" -> {
                    TextPoint text = new TextPoint(
                            new SPoint(new Point(Integer.parseInt(donnees[2]), Integer.parseInt(donnees[3])), 0),
                            donnees[4]
                    );

                    text.getPoint().draw(this.getColorFromString(donnees[5]));
                    objet = text;
                }

                case "line" -> {
                    Straight line = new Straight(
                            new SPoint(new Point(Integer.parseInt(donnees[2]), Integer.parseInt(donnees[3])), 0),
                            new SPoint(new Point(Integer.parseInt(donnees[4]), Integer.parseInt(donnees[5])), 0)
                    );

                    line.getPoint1().draw(this.getColorFromString(donnees[6]));
                    objet = line;
                }

                case "rectangle" -> {
                    SRectangle rec = new SRectangle(
                            new SPoint(new Point(Integer.parseInt(donnees[2]), Integer.parseInt(donnees[3])), 0),
                            new SPoint(new Point(Integer.parseInt(donnees[4]), Integer.parseInt(donnees[5])), 0),
                            Boolean.parseBoolean(donnees[6])
                    );

                    rec.getPoint1().draw(this.getColorFromString(donnees[7]));
                    objet = rec;
                }

                default ->  { }
            }
        }

        suppression = !donnees[0].equals("add");

        if(this.panelDessin != null)
            this.panelDessin.addItem(objet, suppression);
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


    public static void main(String[] args) throws IOException {
        new PanelManager();
        new PanelManager();


        new Thread(() -> {

            try {
                Thread.sleep(15000);
                System.out.println("Troisième client !");
                new PanelManager();
            } catch (Exception e) { e.printStackTrace(); }

        }).start();


    }
}
