package config;


import lombok.SneakyThrows;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader implements PropertyLoader {
    private final Properties properties = new Properties();

    @SneakyThrows (IOException.class)
    private PropertiesLoader(String propertiesFileName) {
        InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
        properties.load(input);
    }

    public static PropertiesLoader load(String propertiesFileName) {
        return new PropertiesLoader(propertiesFileName);
    }

    public static PropertiesLoader load() {
        return new PropertiesLoader("application.properties");
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
