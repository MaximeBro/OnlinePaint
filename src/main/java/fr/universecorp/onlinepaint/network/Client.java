package fr.universecorp.onlinepaint.network;

import fr.universecorp.onlinepaint.ui.PanelManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {

    private Socket socketClient;
    private PanelManager manager;

    public Client() {
    }

    @Override
    public void run() {
        try {
            this.socketClient = new Socket(
                    InetAddress.getLocalHost(),
                    2009);

            this.manager = new PanelManager(this);
            PrintWriter pw = new PrintWriter(this.socketClient.getOutputStream());

            pw.println("start");
            pw.print("client"+this.getName());
            pw.flush();

            Scanner scanner = new Scanner(this.socketClient.getInputStream());
            String msg = scanner.nextLine();

            while(true) {
                if(msg.contains("add") || msg.contains("remove")) {
                    this.manager.setData(msg);
                }

                msg = scanner.nextLine();
            }
        } catch (Exception ignored) { }
    }

    public void action(String action) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socketClient.getOutputStream()));
            writer.write(action);
            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
