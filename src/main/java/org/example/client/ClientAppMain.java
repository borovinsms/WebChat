package org.example.client;

import org.example.server.Settings;

import java.io.IOException;

public class ClientAppMain {

    public static void main(String[] args) throws IOException {
        Settings settings = new Settings();
        final String serverHost = settings.getHost();
        final int serverPort = settings.getPort();
        new Client(serverHost,serverPort);
    }
}
