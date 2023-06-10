package org.example.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Settings {

    private static final String SETTINGS_PATH = "src/main/resources/settings.txt";
    private String host;
    private int port;


    public Settings() throws IOException {
        String result = "";
        Path path = Path.of(SETTINGS_PATH);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        if (!Files.isReadable(path) || (result = Files.readString(path)).isEmpty()) {
            this.host = "localhost";
            this.port = 16800;
            return;
        }
        String[] resArray = result.split(":");
        this.host = resArray[0];
        this.port = Integer.parseInt(resArray[1]);
    }
    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }
}
