package sheet.service;

import sheet.node.NodeType;

import java.util.EnumMap;
import java.util.Map;

public class NodeFactory {
    private static final Map<NodeType, NodeType> childNodeType = new EnumMap<>(NodeType.class);
    private NodeFactory() {

    }

    static {
        childNodeType.put(NodeType.ROOT, NodeType.MAIN_TOPIC);
        childNodeType.put(NodeType.MAIN_TOPIC, NodeType.SUB_TOPIC);
        childNodeType.put(NodeType.FLOATING_TOPIC, NodeType.SUB_TOPIC);
        childNodeType.put(NodeType.SUB_TOPIC, NodeType.SUB_TOPIC);
    }

    public static NodeType getChildNodeType(NodeType type) {
        return childNodeType.get(type);
    }
}

