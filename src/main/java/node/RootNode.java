package node;

import settings.NodeType;
import settings.PropertiesLoader;
import shape.Point;
import shape.Shape;

public class RootNode extends Node implements IRootNode {
    private static final NodeType NODE_TYPE = NodeType.ROOT;

    public RootNode(String text) {
        super(text,NODE_TYPE);
    }

    @Override
    void initShape() {
        int screenWidth = Integer.parseInt(this.propertiesLoader.getProperty("default.SCENE_WIDTH"));
        int screenHeight = Integer.parseInt(this.propertiesLoader.getProperty("default.SCENE_HEIGHT"));
        this.setShape(new Shape(this.getText().length(),new Point(screenWidth/2,screenHeight/2)));
    }
}
