package config;

public interface PropertyLoader {
    String getProperty(String key);

    String getProperty(String key, String defaultValue);
}
