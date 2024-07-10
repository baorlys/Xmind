package node;

import config.NodeType;
import config.Structure;
import java.util.List;

public interface INode {
    void addChild(ChildNode node);
    void removeChild(ChildNode node);

    NodeType getType();

    List<ChildNode> getChildren();

    void changeStructure(Structure structure);

    Structure getStructure();

    String getTopic();

    void setTopic(String text);

    int getId();
}
