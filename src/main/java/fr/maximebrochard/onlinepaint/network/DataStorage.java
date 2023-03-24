package fr.maximebrochard.onlinepaint.network;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class DataStorage {
    public static ArrayList<String> alData = new ArrayList<String>();
    public static ArrayList<String> alClient = new ArrayList<String>();
    public static int numero = 0;

    private Emetteur emetteur;
    private DataRecepteur recepteur;

    public DataStorage(InetAddress groupeIp, int port, String name) throws IOException {
        this.emetteur = new Emetteur(groupeIp, port, name);
        this.recepteur = new DataRecepteur(this, groupeIp, port, name);
    }

    public void supprimerData(String data) {
        if(alData.contains(data)) {
            alData.remove(data);
            try {
                this.emetteur.emettre(data);
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void ajouterData(String data) {
        if(!alData.contains(data)) {
            alData.add(data);
            try {
                this.emetteur.emettre(data);
            } catch (IOException e) { e.printStackTrace(); }
        }

    }

    public void checkClient(String client, String numero) throws IOException {
        if(!alClient.contains(client)) {
            alClient.add(client);
            for(String data : alData) {
                this.emetteur.emettre(data);
            }
        } else {
            this.emetteur.emettre(client + "," + numero + ",supprimer");
        }
    }



    public static void main(String[] args) throws Exception {
        InetAddress groupeIp = InetAddress.getByName("230.125.10.15");
        int port = 2009;

        new DataStorage(groupeIp, port, "");

        while(true) { }
    }
}
