package sheet;

import config.Configuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import sheet.node.ChildNode;
import sheet.node.Node;
import sheet.node.NodeType;
import sheet.relationship.Relationship;
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



    public Sheet(XMind xMind, String name) {
        this.name = name;
        this.xMind = xMind;
        this.nodes = new HashMap<>();
        this.relationships = new HashMap<>();
        this.rootNode = new Node(this, Configuration.ROOT_TOPIC_NAME, NodeType.ROOT);
        viewMode = ViewMode.valueOf(Configuration.SHEET_VIEW_MODE);
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
