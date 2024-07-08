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

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Getter
@Setter
public class Sheet {
    private Logger logger = Logger.getAnonymousLogger();
    private boolean isBalanced;
    private PropertiesLoader propertiesLoader;

    private String name;
    private IRootNode rootTopic;

    HashSet<RelationShip> nodes;

    private ViewMode viewMode;



    public Sheet(String name, IRootNode rootTopic) throws ExceptionOpenFile {
        this.name = name;
        this.rootTopic = rootTopic;
        this.nodes = new HashSet<>();
        propertiesLoader = PropertiesLoader.getInstance();
        isBalanced = true;
        viewMode = ViewMode.valueOf(propertiesLoader.getProperty("default.sheet.view.mode"));
        this.addNewNode(rootTopic);
    }


    public void addNewNode(INode node)  {
        nodes.add(new RelationShip(node));
    }

    public List<INode> getAllNodes() {
        return nodes.stream().map(RelationShip::getNode).collect(Collectors.toList());
    }

    public IChildNode addNodeFrom(INode currentTopic) throws ExceptionOpenFile {
        NodeType nodeType = currentTopic.getType();
        int nodeCount = currentTopic.getChildren().size();
        NodeType childNodeType = AddNodeFactory.getChildNodeType(nodeType);
        IChildNode newNode = new ChildNode(childNodeType.toString().replace("_"," ").toLowerCase()
                + " " + (nodeCount + 1), childNodeType, currentTopic);
        currentTopic.addChild(newNode);
        this.addNewNode(newNode);
        return newNode;
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
        INode nextNode = getNodeByPosition(positionNextNode);
        Optional.ofNullable(currentNode)
                .filter(node -> node.getType() != NodeType.ROOT)
                .ifPresent(node -> Optional.ofNullable(nextNode)
                        .ifPresentOrElse(node1 -> moveNode((IChildNode) currentNode, nextNode),
                                () -> moveNode((IChildNode) currentNode)));

    }

    public void createRelationship(INode fromNode, INode toNode) {
        nodes.stream().filter(listNode -> listNode.getNode().equals(fromNode))
                .forEach(listNode -> {
                    try {
                        listNode.addRelation(toNode);
                    } catch (ExceptionOpenFile e) {
                       logger.warning(e.getMessage());
                    }
                });
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


    public IChildNode insertFloatTopic(Point center) throws ExceptionOpenFile {
        IChildNode floatTopic = new ChildNode(NodeType.FLOATING_TOPIC.toString().replace("_"," ").toLowerCase()
                , NodeType.FLOATING_TOPIC, center);
        this.addNewNode(floatTopic);
        return floatTopic;
    }
}
