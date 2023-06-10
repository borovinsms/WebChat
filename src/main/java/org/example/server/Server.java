package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ArrayList<ClientThread> clients = new ArrayList<>();
    Logger logger = Logger.getInstance();

    public Server(int port) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            logger.log("Server started on port "+port);
            while (true) {
                clientSocket = serverSocket.accept();
                ClientThread client = new ClientThread(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                logger.log("Server stopped");
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMsgToAll(String msg) {
        for (ClientThread cl : clients) {
            cl.sendMessage(msg);
        }
    }

    public void removeClient(ClientThread client) {
        clients.remove(client);
    }
}