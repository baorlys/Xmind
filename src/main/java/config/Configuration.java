package config;


import java.util.Optional;

public class Configuration {
    private static final PropertiesLoader propertyLoader = PropertiesLoader.load();

    public static final String ROOT_TOPIC_NAME =
            Optional.ofNullable(getProperty(ConfigValue.ROOT_TOPIC_NAME.getVariableName()))
                    .orElse("Root Topic");

    public static final String STRUCTURE =
            Optional.ofNullable(getProperty(ConfigValue.STRUCTURE.getVariableName()))
                    .orElse("Mind Map");

    public static final String RELATIONSHIP_NAME =
            Optional.ofNullable(getProperty(ConfigValue.RELATIONSHIP_NAME.getVariableName()))
                    .orElse("Relationship");

    public static final String TOPICS_COUNT =
            Optional.ofNullable(getProperty(ConfigValue.TOPICS_COUNT.getVariableName()))
                    .orElse("1");

    public static final String SHEET_VIEW_MODE =
            Optional.ofNullable(getProperty(ConfigValue.SHEET_VIEW_MODE.getVariableName()))
                    .orElse("Mind Map");

    public static final String SHEET_NAME =
            Optional.ofNullable(getProperty(ConfigValue.SHEET_NAME.getVariableName()))
                    .orElse("Sheet");

    public static final String FLOAT_TOPIC_NAME =
            Optional.ofNullable(getProperty(ConfigValue.FLOAT_TOPIC_NAME.getVariableName()))
            .orElse("Float Topic");


    private Configuration() {

    }


    private static String getProperty(String key) {
        return propertyLoader.getProperty(key);
    }




}
