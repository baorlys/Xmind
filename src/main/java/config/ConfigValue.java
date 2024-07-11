package config;

public enum ConfigValue {
    CONFIG_FILE("application.properties"), // default config file name
    ROOT_TOPIC_NAME("default.root.topic.name"),
    STRUCTURE("default.structure"),
    RELATIONSHIP_NAME("default.relationship.name"),
    TOPICS_COUNT("default.number.of.main.topic"),
    SHEET_VIEW_MODE("default.sheet.view.mode"),
    SHEET_NAME("default.sheet.name"),
    FLOAT_TOPIC_NAME("default.float.topic.name");


    private final String variableName;
    ConfigValue(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}
