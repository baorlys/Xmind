package sheet.node;

public enum NodeType {
    ROOT ("Root Topic"), // Need to add
    MAIN_TOPIC ("Main Topic"),
    SUB_TOPIC ("Sub Topic"),
    FLOATING_TOPIC ("Floating Topic");

    private final String name;

    NodeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
