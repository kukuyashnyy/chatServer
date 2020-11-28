package org.itstep;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private String userName;
    private String serverHost;
    private int serverPort;
    private Scanner userInputScanner;

    public static void main(String[] args) throws IOException, InterruptedException {
        String readName = null;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input username: ");
        while (readName == null || readName.trim().equals("")){
            readName = scan.nextLine();
            if (readName.trim().equals("")){
                System.out.println("Invalid. Please enter again: ");
            }
        }

        Client client = new Client(readName);
        client.startClient(scan);
    }

    public Client(String userName) {
        this.userName = userName;
        this.serverHost = Settings.HOST;
        this.serverPort = Settings.PORT;
    }

    private void startClient(Scanner scan) throws IOException, InterruptedException {
        Socket socket = new Socket(serverHost, serverPort);
        Thread.sleep(1000);

        ServerThread serverThread = new ServerThread(socket, userName);
        Thread serverAccessThread = new Thread(serverThread);
        serverAccessThread.start();

        while (serverAccessThread.isAlive()){
            if (scan.hasNextLine()){
                serverThread.addNextMessage(scan.nextLine());
            }
        }

    }
}
