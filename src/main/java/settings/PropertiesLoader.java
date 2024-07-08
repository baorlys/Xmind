package settings;

import exceptions.ExceptionOpenFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private final Properties properties = new Properties();

    private PropertiesLoader(String propertiesFileName) throws ExceptionOpenFile{
        InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
        if (input == null) {
            throw new ExceptionOpenFile(new Exception("Unable to find " + propertiesFileName));
        }
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new ExceptionOpenFile(e);
        }
    }

    public static PropertiesLoader getInstance() throws ExceptionOpenFile {
        return new PropertiesLoader("application.properties");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
