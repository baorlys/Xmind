package relationship;

import config.Configuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import node.INode;
import sheet.Sheet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Relationship {
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private static final AtomicInteger uniqueId = new AtomicInteger(0);
    @Setter(AccessLevel.NONE)
    private int id;

    private Sheet sheet;
    private Map<INode, INode> relation;
    String name;
    private Configuration configuration;


    public Relationship(Sheet sheet, INode fromNode, INode toNode) {
        this.configuration = sheet.getConfiguration();
        this.relation = new HashMap<>();
        relation.put(fromNode, toNode);
        this.name = configuration.getRelationshipName();
        this.id = uniqueId.getAndIncrement();
    }

    public static void resetUniqueId() {
        uniqueId.set(0);
    }



}
