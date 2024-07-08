package xmind;

import com.fasterxml.jackson.annotation.*;
import config.Configuration;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Sheet {
    @JsonProperty("id")
    private String id;
    private String name;

    @JsonBackReference
    private IRootNode rootTopic;

    @JsonManagedReference
    HashSet<Relationship> nodes;
    private ViewMode viewMode;

    private Configuration configuration = new Configuration();



    public Sheet(String name, IRootNode rootTopic) {
        this.name = name;
        this.rootTopic = rootTopic;
        this.nodes = new HashSet<>();
        viewMode = ViewMode.valueOf(configuration.getDefaultSheetViewMode());
        this.addNodeToList(rootTopic);
    }


    public void addNodeToList(INode node) {
        nodes.add(new Relationship(node));
    }

    public List<INode> getAllNodes() {
        return nodes.stream().map(Relationship::getNode).collect(Collectors.toList());
    }

    public IChildNode addNodeFrom(INode currentTopic)  {
        NodeType nodeType = currentTopic.getType();
        int nodeCount = currentTopic.getChildren().size();
        NodeType childNodeType = AddNodeFactory.getChildNodeType(nodeType);
        IChildNode newNode = new ChildNode(childNodeType.toString().replace("_"," ").toLowerCase()
                + " " + (nodeCount + 1), childNodeType, currentTopic);
        currentTopic.addChild(newNode);
        this.addNodeToList(newNode);
        return newNode;
    }

    public void removeNode(IChildNode topic) {
        topic.removeParent();
        nodes.removeIf(listNode -> listNode.getNode().equals(topic));
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

    public void createRelationship(INode fromNode, INode toNode) {
        nodes.stream().filter(listNode -> listNode.getNode().equals(fromNode))
                .forEach(listNode -> listNode.addRelation(toNode));
    }

    public Relationship getRelationshipsOf(INode node) {
        return nodes.stream().filter(listNode -> listNode.getNode().equals(node))
                .findFirst().orElse(null);
    }

    public void removeRelationship(INode fromNode, INode toNode) {
        nodes.stream().filter(listNode -> listNode.getNode().equals(fromNode))
                .forEach(listNode -> listNode.removeRelation(toNode));
    }

    public void changeRelationshipName(INode fromNode, INode toNode, String relationshipName) {
        nodes.stream().filter(listNode -> listNode.getNode().equals(fromNode))
                .forEach(listNode -> listNode.changeRelationName(toNode, relationshipName));

    }



    public void changeViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public ViewMode getViewMode() {
        return viewMode;
    }


    public INode getNodeByPosition(Point position) {
        return nodes.stream().map(Relationship::getNode)
                .filter(node -> node.getShape().isContainPoint(position))
                .findFirst().orElse(null);
    }


    public IChildNode insertFloatTopic(Point center)  {
        IChildNode floatTopic = new ChildNode(NodeType.FLOATING_TOPIC.toString().replace("_"," ").toLowerCase()
                , NodeType.FLOATING_TOPIC, center);
        this.addNodeToList(floatTopic);
        return floatTopic;
    }


}
