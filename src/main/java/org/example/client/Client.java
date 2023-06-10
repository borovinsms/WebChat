package org.example.client;

import org.example.server.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

    Logger logger = Logger.getInstance();




    private Socket socket;
    private BufferedReader in;
    private PrintWriter writer;
    private BufferedReader input;
    private String clientNickName;


    public Client(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            input = new BufferedReader(new InputStreamReader(System.in));
            writer = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.addNickName();
            new Thread(new MsgFromServer()).start();
            new Thread(new MsgToServer()).start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Client.this.exitClient();
        }
    }

    public void addNickName() throws IOException {
        logger.log("New client connected. To exit type [exit]. Enter nickname ");
        clientNickName = input.readLine();
        writer.write("Hello " + clientNickName + "\n");
        writer.flush();

    }

    public void exitClient() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class MsgFromServer implements Runnable {
        @Override
        public void run() {
            String msg;
            try {
                while (true) {
                    msg = in.readLine();
                    if (msg.equals("exit")) {
                        Client.this.exitClient();
                        break;
                    }
                    logger.log(msg);
                }
            } catch (IOException e) {
                Client.this.exitClient();
            }
        }
    }

    public class MsgToServer implements Runnable {

        @Override
        public void run() {
            while (true) {
                String userMessage;
                try {
                    userMessage = input.readLine();
                    if (userMessage.equals("exit")) {
                        writer.write("exit" + "\n");
                        Client.this.exitClient();
                        break;
                    } else {
                        writer.write((clientNickName + ": " + userMessage + "\n"));
                    }
                    writer.flush();
                } catch (Exception e) {
                    Client.this.exitClient();
                }
            }
        }
    }
}
