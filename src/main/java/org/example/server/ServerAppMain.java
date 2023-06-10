package org.example.server;

import java.io.IOException;

public class ServerAppMain {

    public static void main(String[] args) throws IOException {
        Settings settings = new Settings();
        new Server(settings.getPort());
    }
}
