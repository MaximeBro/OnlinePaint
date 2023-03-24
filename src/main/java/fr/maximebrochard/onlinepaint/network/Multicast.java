package fr.maximebrochard.onlinepaint.network;

import fr.maximebrochard.onlinepaint.ui.PanelManager;

import java.io.IOException;
import java.net.InetAddress;

public class Multicast {
    private Emetteur emetteur;
    private Recepteur recepteur;
    private PanelManager manager;

    public Multicast(PanelManager manager, InetAddress groupeIp, int port, String nom) throws IOException {
        this.manager = manager;
        this.emetteur = new Emetteur(groupeIp, port, nom);
        this.recepteur = new Recepteur(this.manager, groupeIp, port, nom);
    }

    public Emetteur getEmetteur() { return this.emetteur; }
    public Recepteur getRecepteur() { return this.recepteur; }
}
