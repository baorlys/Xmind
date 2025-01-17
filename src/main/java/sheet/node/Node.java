package sheet.node;

import config.Configuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import sheet.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Node implements INode {
    @Setter(AccessLevel.NONE)
    private UUID id;
    private Sheet sheet;
    private String topic;


    private List<ChildNode> children;

    private NodeType type;

    private Structure structure;
    public Node(Sheet sheet, String topic, NodeType type) {
        this.id = UUID.randomUUID();
        this.topic = topic;
        this.sheet = sheet;
        this.type = type;

        this.children = new ArrayList<>();
        this.structure = Structure.valueOf(Configuration.STRUCTURE);
    }


    public void setTopic(String topic) {
        this.topic = topic;
    }


    @Override
    public void addChild(ChildNode node) {
        node.setParent(this);

        this.children.add(node);
    }

    @Override
    public void removeChild(ChildNode node) {
        this.children.remove(node);
    }

    @Override
    public void changeStructure(Structure structure) {
        this.structure = structure;

        children.forEach(child -> child.changeStructure(structure));
    }


}
