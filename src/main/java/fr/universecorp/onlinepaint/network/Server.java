package fr.universecorp.onlinepaint.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends ServerSocket {

    private ArrayList<String> alData;

    public Server(int port) throws IOException {
        super(port);
        this.alData = new ArrayList<String>();
    }



    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket connexion;

        ArrayList<String> alData = new ArrayList<String>();

        try {
            serverSocket = new ServerSocket(2009);
            connexion = serverSocket.accept();

            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            // Taper "stop" dans la console du serveur pour le stopper !
            while(!command.equalsIgnoreCase("stop")) {

                Thread client = new Thread(() -> {

                    System.out.println("client connecté !");
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(connexion.getInputStream()));

                        String line = br.readLine();
                        while(!line.equalsIgnoreCase("exit")) {
                            if(line.equalsIgnoreCase("start")) {
                                BufferedWriter brW = new BufferedWriter(new OutputStreamWriter(connexion.getOutputStream()));
                                for(String data : alData)
                                    brW.write(data);

                                brW.close();
                            } else {
                                alData.add(line);
                            }
                        }
                    } catch(Exception ignored) {}

                });
                client.interrupt();

                command = scanner.nextLine();
            }

            connexion.close();
            serverSocket.close();
        } catch (Exception e) { e.printStackTrace(); }




    }
}
