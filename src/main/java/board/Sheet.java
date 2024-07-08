package board;

import exceptions.ExceptionOpenFile;
import lombok.Getter;
import lombok.Setter;
import node.*;
import settings.PropertiesLoader;
import shape.Point;
import settings.NodeType;
import settings.Structure;
import settings.ViewMode;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Sheet {
    private boolean isBalanced;
    private PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
    private String name;
    private IRootNode rootTopic;

    HashSet<RelationShip> nodes;

    private ViewMode viewMode = ViewMode.valueOf(propertiesLoader.getProperty("default.sheet.view.mode"));


    public Sheet(String name) throws IOException, ExceptionOpenFile {
        this.name = name;
    }
    public Sheet(String name, IRootNode rootTopic) throws ExceptionOpenFile, IOException {
        this.name = name;
        this.rootTopic = rootTopic;
        this.nodes = new HashSet<>();
        this.addNewNode(rootTopic);
    }


    public void addNewNode(INode node) throws ExceptionOpenFile, IOException {
        nodes.add(new RelationShip(node));
    }

    public List<INode> getAllNodes() {
        return nodes.stream().map(RelationShip::getNode).collect(Collectors.toList());
    }

    public IChildNode addNodeToCurrentTopic(INode currentTopic) throws ExceptionOpenFile, IOException {
        NodeType nodeType = currentTopic.getType();
        int nodeCount = currentTopic.getChildren().size();
        NodeType childNodeType = AddNodeFactory.getChildNodeType(nodeType);
        IChildNode newNode = new ChildNode(childNodeType.toString().replace("_"," ").toLowerCase()
                + " " + (nodeCount + 1), childNodeType, currentTopic);
        currentTopic.addChild(newNode);
        this.addNewNode(newNode);
        return newNode;
    }



    public IChildNode addFloatTopic() throws ExceptionOpenFile, IOException {
        IChildNode floatTopic = new ChildNode(NodeType.FLOATING_TOPIC.toString().replace("_"," ").toLowerCase()
              , NodeType.FLOATING_TOPIC, null);
        this.addNewNode(floatTopic);
        return floatTopic;
    }

    public void removeNode(IChildNode topic) {
        topic.removeParent();
        nodes.removeIf(listNode -> listNode.getNode().equals(topic));
    }

    public void moveNode(IChildNode nodeIsMoved, INode parentNode) {
        nodeIsMoved.moveToParent(parentNode);
    }
    public void moveNode(IChildNode nodeIsMoved) {
        nodeIsMoved.removeParent();
    }

    public void moveNode(Point positionCurrentNode, Point positionNextNode) {
        INode currentNode = getNodeByPosition(positionCurrentNode);
        if (currentNode == null || currentNode.getType() == NodeType.ROOT) {
            return;
        }

        INode nextNode = getNodeByPosition(positionNextNode);
        if (nextNode == null) {
            moveNode((IChildNode) currentNode);
        }

        moveNode((IChildNode) currentNode, nextNode);

    }

    public void createRelationship(INode fromNode, INode toNode) {
        nodes.stream().filter(listNode -> listNode.getNode().equals(fromNode))
                .forEach(listNode -> listNode.addRelation(toNode));
    }


    public INode getNode(INode node) {
        return nodes.stream().map(RelationShip::getNode)
                .filter(listNode -> listNode.equals(node))
                .findFirst().orElse(null);
    }

    public RelationShip getRelationships(INode node) {
        return nodes.stream().filter(listNode -> listNode.getNode().equals(node))
                .findFirst().orElse(null);
    }

    public void removeRelationship(INode node1, INode node2) {
        nodes.stream().filter(listNode -> listNode.getNode().equals(node1))
                .forEach(listNode -> listNode.removeRelation(node2));
    }

    public void changeRelationshipName(INode node1, INode node2, String relationshipName) {
        nodes.stream().filter(listNode -> listNode.getNode().equals(node1))
                .forEach(listNode -> listNode.changeRelationName(node2, relationshipName));

    }


    public void balanceMap() {
        isBalanced = true;
    }

    public void changeViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public ViewMode getViewMode() {
        return viewMode;
    }


    public INode getNodeByPosition(Point positionClick) {
        return nodes.stream().map(RelationShip::getNode)
                .filter(node -> node.getShape().isContainPoint(positionClick))
                .findFirst().orElse(null);
    }


    public void changeStructure(INode node, Structure structure) {
        node.changeStructure(structure);
    }


}
