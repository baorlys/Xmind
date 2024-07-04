package node;

import settings.NodeType;

public class RootNode extends Node implements IRootNode {
    // Default color is black
    private static final NodeType NODE_TYPE = NodeType.ROOT;

    public RootNode(String text) {
        super(text,NODE_TYPE);
    }


}
