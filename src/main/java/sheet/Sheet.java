package sheet;

import config.Configuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import node.*;
import config.NodeType;
import config.ViewMode;
import relationship.Relationship;
import xmind.XMind;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Sheet {
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private static final AtomicInteger uniqueId = new AtomicInteger(0);

    private int id;

    private XMind xMind;
    private String name;

    private Node rootNode;

    Map<Integer, Relationship> relationships;

    Map<Integer, ChildNode> nodes;
    private ViewMode viewMode;


    private Configuration configuration ;

    public Sheet(XMind xMind, String name) {
        this.name = name;
        this.nodes = new HashMap<>();
        this.relationships = new HashMap<>();
        this.configuration = xMind.getConfiguration();
        this.rootNode = new Node(this, configuration.getRootTopicName(), NodeType.ROOT);
        viewMode = ViewMode.valueOf(configuration.getSheetViewMode());
        this.id = uniqueId.getAndIncrement();
    }

    public static void resetUniqueId() {
        uniqueId.set(0);
    }

    public void addNodeToSheet(ChildNode node) {
        nodes.put(node.getId(), node);
    }


    public void addRelationship(Relationship relationship) {
        relationships.put(relationship.getId(), relationship);
    }




}
