package de.uniba.dsg.search;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Configuration {
    private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());

    public static Properties loadProperties() {
        try (InputStream stream = CustomSpotifyApi.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            if (stream != null) {
                properties.load(stream);
            }
            return properties;
        } catch (IOException e) {
            LOGGER.severe("Cannot load configuration file.");
            return null;
        }
    }
}
