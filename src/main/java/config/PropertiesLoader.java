package config;


import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader implements PropertyLoader {
    private final Properties properties = new Properties();

    @SneakyThrows
    private PropertiesLoader(String propertiesFileName) {
        if (propertiesFileName == null || propertiesFileName.isEmpty()) {
            throw new IllegalArgumentException("Properties file name cannot be null or empty");
        }

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (input == null) {
                throw new IOException("Property file '" + propertiesFileName + "' not found in the classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IOException(e);
        }

    }


    public static PropertiesLoader getInstance() {
        return new PropertiesLoader("application.properties");
    }

    @Override
    public String getProperty(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Property key cannot be null or empty");
        }
        return properties.getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Property key cannot be null or empty");
        }
        return properties.getProperty(key, defaultValue);
    }
}
