package fr.maximebrochard.onlinepaint.network;

import fr.maximebrochard.onlinepaint.ui.PanelManager;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Recepteur extends Thread {

    private InetAddress groupeIp;
    private int port;
    private String nom;
    private MulticastSocket socketReception;
    private PanelManager manager;

    public Recepteur(PanelManager manager, InetAddress ip, int port, String nom) throws IOException {
        this.manager = manager;
        this.groupeIp = ip;
        this.port = port;
        this.nom = nom;

        this.socketReception = new MulticastSocket(this.port);
        this.socketReception.joinGroup(this.groupeIp);
        start();
    }


    @Override
    public void run() {
        DatagramPacket message;
        byte[] contenuMessage;
        String texte;

        while(true) {
            contenuMessage = new byte[1024];
            message = new DatagramPacket(contenuMessage, contenuMessage.length);

            try {
                this.socketReception.receive(message);
                texte = (new DataInputStream(new ByteArrayInputStream(contenuMessage))).readUTF();

                if(!texte.contains("data,"))
                    this.manager.setData(texte);

            } catch(Exception e) { e.printStackTrace(); }

        }
    }

}
