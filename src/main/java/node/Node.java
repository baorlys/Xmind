package node;

import config.Configuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import config.NodeType;
import config.Structure;
import sheet.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Node implements INode {
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private static final AtomicInteger uniqueId = new AtomicInteger(0);
    @Setter(AccessLevel.NONE)
    private int id;
    private Sheet sheet;
    private String topic;


    private List<ChildNode> children;

    private NodeType type;

    private Structure structure;
    private Configuration configuration;
    public Node(Sheet sheet, String topic, NodeType type) {
        this.topic = topic;
        this.children = new ArrayList<>();
        this.type = type;
        this.configuration = sheet.getConfiguration();
        this.structure = Structure.valueOf(configuration.getStructure());
        this.id = uniqueId.getAndIncrement();
    }

    public static void resetUniqueId() {
        uniqueId.set(0);
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
