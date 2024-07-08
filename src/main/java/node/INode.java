package node;

import config.NodeType;
import config.Structure;
import shape.Shape;

import java.util.List;

public interface INode {
    void addChild(IChildNode node);
    void removeChild(IChildNode node);

    NodeType getType();

    List<IChildNode> getChildren();

    Shape getShape();

    void changeStructure(Structure structure);

    Structure getStructure();

    String getText();

    void setText(String text);
}
