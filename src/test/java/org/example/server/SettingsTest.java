package org.example.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SettingsTest {

    @Test
    void settingFileRead() throws IOException {
        Settings serverSettings = new Settings();
        String settings = "localhost:16800";
        String[] settingsArray = settings.split(":");

        Assertions.assertEquals(settingsArray[0],serverSettings.getHost());
        Assertions.assertEquals(Integer.parseInt(settingsArray[1]),serverSettings.getPort());
    }
}
