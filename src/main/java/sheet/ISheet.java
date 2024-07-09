package sheet;

import config.ViewMode;
import node.IChildNode;
import node.INode;
import node.IRootNode;
import shape.Point;

import java.util.List;

public interface ISheet {
    void addNodeToSheet(INode node);
    void removeNode(IChildNode node);
    List<INode> getAllNodes();
    IRootNode getRootNode();
    String getName();
    IChildNode addNodeFrom(INode node);
    IChildNode insertFloatingNode(Point position);
    void moveNode(IChildNode node, INode destinationNode);
    void moveNode(IChildNode node);
    void moveNode(Point positionNodeMoved, Point positionDestination);
    void changeNodeName(INode node, String name);
    void createRelationship(INode node, INode relatedNode);
    void changeRelationshipName(INode node, INode relatedNode, String name);
    void removeRelationship(INode node, INode relatedNode);
    boolean isRelated(INode node, INode relatedNode);
    Relationship getRelationship(INode node);
    void setViewMode(ViewMode viewMode);
    ViewMode getViewMode();
    INode getNodeByPosition(Point position);


}
