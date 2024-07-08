package node;

import exceptions.ExceptionOpenFile;
import settings.NodeType;
import shape.Point;
import shape.Shape;

import java.io.IOException;

public class RootNode extends Node implements IRootNode {
    private static final NodeType NODE_TYPE = NodeType.ROOT;

    public RootNode(String text) throws ExceptionOpenFile, IOException {
        super(text,NODE_TYPE);
        initShape();
    }

    @Override
    void initShape() {
        int screenWidth = Integer.parseInt(this.getPropertiesLoader().getProperty("default.screen.width"));
        int screenHeight = Integer.parseInt(this.getPropertiesLoader().getProperty("default.screen.height"));
        this.setShape(new Shape(this.getText().length(),new Point(screenWidth/2,screenHeight/2)));
    }
}
