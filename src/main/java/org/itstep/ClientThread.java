package org.itstep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {

    private Socket socket;
    private ChatServer server;
    private PrintWriter clientOut;

    public ClientThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    public PrintWriter getWriter() {
        return clientOut;
    }

    @Override
    public void run() {
        try {
            this.clientOut = new PrintWriter(socket.getOutputStream(), false);
            Scanner in = new Scanner(socket.getInputStream());

            while (!socket.isClosed()) {
                if (in.hasNextLine()){
                    String input = in.nextLine();
                    if (input != null) {
                        for (ClientThread thatClient : server.getClients()){
                            PrintWriter thatClientOut = thatClient.getWriter();
                            if (thatClientOut != null){
                                thatClientOut.write(input + "\r\n");
                                thatClientOut.flush();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
