package org.itstep;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class ChatServer
{
    private List<ClientThread> clients;
    private final int serverPort = Settings.PORT;

    public static void main( String[] args ) throws IOException {
        ChatServer server = new ChatServer();
        server.startServer();
    }

    public List<ClientThread> getClients() {
        return clients;
    }

    private void startServer() throws IOException {
        clients = new ArrayList<ClientThread>();
        ServerSocket serverSocket = new ServerSocket(serverPort);
        acceptClients(serverSocket);
    }

    public void acceptClients(ServerSocket serverSocket) throws IOException {
        System.out.println("server starts port = " + serverSocket.getLocalSocketAddress());
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("accepts : " + socket.getRemoteSocketAddress());
            ClientThread client = new ClientThread(socket, this);
            Thread thread = new Thread(client);
            thread.start();
            clients.add(client);
        }
    }
}
