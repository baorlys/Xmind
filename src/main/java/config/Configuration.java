package config;


public class Configuration {
    private final PropertyLoader propertyLoader;

    public Configuration(PropertyLoader propertyLoader) {
        this.propertyLoader = propertyLoader;
    }
    private String getProperty(String key) {
        return propertyLoader.getProperty(key);
    }


    public String getRootTopicName() {
        return getProperty("default.root.topic.name");
    }

    public String getStructure() {
        return getProperty("default.structure");
    }

    public String getRelationshipName() {
        return getProperty("default.relationship.name");
    }

    public String getTopicCount() {
        return getProperty("default.number.of.main.topic");
    }

    public String getSheetViewMode() {
        return getProperty("default.sheet.view.mode");
    }

    public String getSheetName() {
        return getProperty("default.sheet.name");
    }

    public String getXMindName() {
        return getProperty("default.xMind.name");
    }


}
