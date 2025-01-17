package sheet.service;

import config.Configuration;
import sheet.Sheet;
import sheet.node.ChildNode;
import sheet.node.INode;
import sheet.node.NodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SheetHandler {
    private SheetHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static int getNodesCount(Sheet sheet) {
        return sheet.getNodes().size();
    }
    public static void changeNodeTopic(INode node, String topic) {
        node.setTopic(topic);
    }


    public static void removeNode(Sheet sheet, ChildNode node) {
        node.removeParent();
        sheet.getNodes().remove(node.getId());
    }

    public static void moveTo(ChildNode node, INode newParent) {
        node.moveTo(newParent);
    }

    public static void toFloatingNode(ChildNode node) {
        node.removeParent();
    }



    public static void addNodeFrom(Sheet sheet, INode currentTopic) {
        NodeType nodeType = currentTopic.getType();
        int nodeCount = currentTopic.getChildren().size();
        NodeType childNodeType = NodeFactory.getChildNodeType(nodeType);
        String childTopic = childNodeType.toString().replace("_"," ").toLowerCase() + " " + (nodeCount + 1);
        ChildNode newNode = new ChildNode(sheet, childTopic, childNodeType, currentTopic);
        currentTopic.addChild(newNode);
        sheet.addNodeToSheet(newNode);
    }

    public static ChildNode getLastNodeAdded(Sheet sheet) {
        List<ChildNode> nodes = new ArrayList<>(sheet.getNodes().values());
        return nodes.get(nodes.size() - 1);
    }

    public static INode getNodeById(Sheet firstSheet, UUID id) {
        return firstSheet.getNodes().get(id);
    }


    public static void insertFloatingNode(Sheet sheet) {
        ChildNode floatingNode = new ChildNode(sheet, Configuration.FLOAT_TOPIC_NAME, NodeType.FLOATING_TOPIC);
        sheet.addNodeToSheet(floatingNode);
    }
}
