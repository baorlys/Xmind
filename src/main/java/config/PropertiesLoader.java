package config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesLoader implements PropertyLoader {
    private final Properties properties = new Properties();

    private PropertiesLoader(String propertiesFileName) {
        Objects.requireNonNull(propertiesFileName);
        InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
        try {
            properties.load(input);
        } catch (IOException e) {
            ExceptionUtils.sneakyThrow(e);
        }
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
