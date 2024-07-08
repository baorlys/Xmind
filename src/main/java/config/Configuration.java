package config;


public class Configuration {
    private final PropertyLoader propertyLoader;

    public Configuration() {
        this.propertyLoader = PropertiesLoader.getInstance();
    }


    public String getDefaultRootTopicName() {
        return propertyLoader.getProperty("default.root.topic.name");
    }

    public String getDefaultStructure() {
        return propertyLoader.getProperty("default.structure");
    }

    public String getDefaultRelationshipName() {
        return propertyLoader.getProperty("default.relationship.name");
    }

    public int getDefaultNumberOfMainTopic() {
        return Integer.parseInt(propertyLoader.getProperty("default.number.of.main.topic"));
    }

    public int getDefaultScreenWidth() {
        return Integer.parseInt(propertyLoader.getProperty("default.screen.width"));
    }

    public int getDefaultScreenHeight() {
        return Integer.parseInt(propertyLoader.getProperty("default.screen.height"));
    }

    public String getDefaultSheetViewMode() {
        return propertyLoader.getProperty("default.sheet.view.mode");
    }

    public String getDefaultSheetName() {
        return propertyLoader.getProperty("default.sheet.name");
    }


    public String getDefaultXMindName() {
        return propertyLoader.getProperty("default.xmind.name");
    }
}
