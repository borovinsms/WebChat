package org.example.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;


public class Logger {
    final Path logPath = Path.of("/src/main/resources/log.txt");
    private static Logger logger = null;

    private Logger() {

    }

    public void log(String message) {
        synchronized (logPath) {
            try {
                String newMessage = message +
                        "\n" +
                        LocalDateTime.now() +
                        "\n";
                System.out.print(newMessage);
                if (!Files.exists(logPath)) {
                    Files.createFile(logPath);
                }
                if (Files.exists(logPath) && Files.isWritable(logPath)) {
                    Files.write(logPath, Collections.singleton(newMessage), StandardOpenOption.APPEND);
                }
            } catch (IOException exception) {
                this.log(exception.getMessage());
                throw new RuntimeException(exception);
            }
        }
    }

    public static Logger getInstance() {
        if (logger == null) logger = new Logger();
        return logger;
    }

}
