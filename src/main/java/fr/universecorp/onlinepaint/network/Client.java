package fr.universecorp.onlinepaint.network;

import fr.universecorp.onlinepaint.ui.PanelManager;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client extends Thread {

    private ArrayList<String> alData;

    public Client() {
        this.alData = new ArrayList<String >();
    }

    @Override
    public void run() {
        Socket socketCoteClient;
        try {
            socketCoteClient = new Socket(
                    InetAddress.getLocalHost(),
                    2009);

            PanelManager manager = new PanelManager(this);
            PrintWriter pw = new PrintWriter(socketCoteClient.getOutputStream());

            pw.println("start");
            pw.print("client"+this.getName());

            Scanner scanner = new Scanner(socketCoteClient.getInputStream());
            String msg = scanner.nextLine();

            while(true) {


                msg = scanner.nextLine();
            }
        } catch (Exception ignored) { }
    }


    public void setData(ArrayList<String> data) { this.alData = data; }
}
