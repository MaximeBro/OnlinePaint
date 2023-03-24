package fr.maximebrochard.onlinepaint.network;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Emetteur extends Thread {

    private InetAddress groupeIp;
    private int port;
    private String nom;
    private MulticastSocket socketEmission;

    public Emetteur(InetAddress ip, int port, String nom) throws IOException {
        this.groupeIp = ip;
        this.port = port;
        this.nom = nom;

        this.socketEmission = new MulticastSocket();
        this.socketEmission.setTimeToLive(15);
        start();
    }

    @Override
    public void run() {
        BufferedReader entreeClavier;

        try {
            entreeClavier = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                String texte = entreeClavier.readLine();
                emettre(texte);
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void emettre(String texte) throws IOException {
        byte[] contenuMessage;
        DatagramPacket message;

        ByteArrayOutputStream sortie = new ByteArrayOutputStream();
        (new DataOutputStream(sortie)).writeUTF(texte);
        contenuMessage = sortie.toByteArray();
        message = new DatagramPacket(contenuMessage, contenuMessage.length, this.groupeIp, this.port);
        socketEmission.send(message);
    }

}
