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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Sheet {
    @Setter(AccessLevel.NONE)
    private UUID id;

    private XMind xMind;
    private String name;

    private Node rootNode;

    Map<UUID, Relationship> relationships;

    Map<UUID, ChildNode> nodes;
    private ViewMode viewMode;



    public Sheet(XMind xMind, String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.xMind = xMind;
        this.nodes = new HashMap<>();
        this.relationships = new HashMap<>();
        this.rootNode = new Node(this, Configuration.ROOT_TOPIC_NAME, NodeType.ROOT);
        viewMode = ViewMode.valueOf(Configuration.SHEET_VIEW_MODE);
    }

    public void addNodeToSheet(ChildNode node) {
        nodes.put(node.getId(), node);
    }


    public void addRelationship(Relationship relationship) {
        relationships.put(relationship.getId(), relationship);
    }




}
