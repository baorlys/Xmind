package sheet.node;

import config.Configuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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
    public Node(Sheet sheet, String topic, NodeType type) {
        this.topic = topic;
        this.sheet = sheet;
        this.type = type;

        this.children = new ArrayList<>();
        this.structure = Structure.valueOf(Configuration.STRUCTURE);
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
