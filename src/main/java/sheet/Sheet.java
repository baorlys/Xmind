package sheet;

import com.fasterxml.jackson.annotation.*;
import config.Configuration;
import config.PropertiesLoader;
import lombok.Getter;
import lombok.Setter;
import node.*;
import shape.Point;
import config.NodeType;
import config.ViewMode;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Sheet implements ISheet {
    private String name;

    private IRootNode rootTopic;

    HashSet<Relationship> nodes;
    private ViewMode viewMode;
    @JsonIgnore
    private Configuration configuration ;



    public Sheet(String name, IRootNode rootTopic) {
        this.name = name;
        this.rootTopic = rootTopic;
        this.nodes = new HashSet<>();
        this.configuration = new Configuration(PropertiesLoader.load());
        viewMode = ViewMode.valueOf(configuration.getDefaultSheetViewMode());
        this.addNodeToSheet(rootTopic);
    }




    public List<INode> getAllNodes() {
        return nodes.stream().map(Relationship::getNode).collect(Collectors.toList());
    }

    @Override
    public void addNodeToSheet(INode node) {
        nodes.add(new Relationship(node));

    }

    @Override
    public void removeNode(IChildNode node) {
        node.removeParent();
        nodes.removeIf(listNode -> listNode.getNode().equals(node));
    }

    @Override
    public IRootNode getRootNode() {
        return rootTopic;
    }

    public IChildNode addNodeFrom(INode currentTopic)  {
        NodeType nodeType = currentTopic.getType();
        int nodeCount = currentTopic.getChildren().size();
        NodeType childNodeType = AddNodeFactory.getChildNodeType(nodeType);
        IChildNode newNode = new ChildNode(childNodeType.toString().replace("_"," ").toLowerCase()
                + " " + (nodeCount + 1), childNodeType, currentTopic);
        currentTopic.addChild(newNode);
        this.addNodeToSheet(newNode);
        return newNode;
    }

    @Override
    public IChildNode insertFloatingNode(Point position) {
        IChildNode floatingNode = new ChildNode(NodeType.FLOATING_TOPIC.toString().replace("_"," ").toLowerCase()
                , NodeType.FLOATING_TOPIC, position);
        this.addNodeToSheet(floatingNode);
        return floatingNode;
    }



    public void moveNode(IChildNode nodeMoved, INode destinationNode) {
        nodeMoved.moveTo(destinationNode);
    }
    public void moveNode(IChildNode nodeMoved) {
        nodeMoved.removeParent();
    }

    public void moveNode(Point positionNodeMoved, Point positionDestination) {
        INode currentNode = getNodeByPosition(positionNodeMoved);
        INode nextNode = getNodeByPosition(positionDestination);
        Optional.ofNullable(currentNode)
                .filter(node -> node.getType() != NodeType.ROOT)
                .ifPresent(node -> Optional.ofNullable(nextNode)
                        .ifPresentOrElse(node1 -> moveNode((IChildNode) currentNode, nextNode),
                                () -> moveNode((IChildNode) currentNode)));

    }

    @Override
    public void changeNodeName(INode node, String name) {
        nodes.stream().filter(listNode -> listNode.getNode().equals(node))
                .forEach(listNode -> listNode.getNode().setText(name));

    }

    public void createRelationship(INode fromNode, INode toNode) {
        nodes.stream().filter(listNode -> listNode.getNode().equals(fromNode))
                .forEach(listNode -> listNode.addRelation(toNode));
    }


    public void removeRelationship(INode fromNode, INode toNode) {
        nodes.stream().filter(listNode -> listNode.getNode().equals(fromNode))
                .forEach(listNode -> listNode.removeRelation(toNode));
    }

    @Override
    public boolean isRelated(INode node, INode relatedNode) {
        return nodes.stream().filter(listNode -> listNode.getNode().equals(node))
                .anyMatch(listNode -> listNode.isRelated(relatedNode));
    }

    @Override
    public Relationship getRelationship(INode node) {
        return nodes.stream().filter(listNode -> listNode.getNode().equals(node))
                .findFirst().orElse(null);
    }

    public void changeRelationshipName(INode fromNode, INode toNode, String relationshipName) {
        nodes.stream().filter(listNode -> listNode.getNode().equals(fromNode))
                .forEach(listNode -> listNode.changeRelationName(toNode, relationshipName));

    }



    public INode getNodeByPosition(Point position) {
        return nodes.stream().map(Relationship::getNode)
                .filter(node -> node.getShape().isContainPoint(position))
                .findFirst().orElse(null);
    }




}
