package node;

import config.Configuration;
import config.NodeType;
import config.PropertiesLoader;
import shape.Point;
import shape.Shape;


public class RootNode extends Node implements IRootNode {
    private static final NodeType NODE_TYPE = NodeType.ROOT;
    Configuration configuration;

    public RootNode(String text) {
        super(text,NODE_TYPE);
        this.configuration = new Configuration(PropertiesLoader.load());
        initShape();
    }


    @Override
    void initShape() {
        int screenWidth = configuration.getDefaultScreenWidth();
        int screenHeight = configuration.getDefaultScreenHeight();
        this.setShape(new Shape(this.getText().length(),new Point(screenWidth/2,screenHeight/2)));
    }
}
