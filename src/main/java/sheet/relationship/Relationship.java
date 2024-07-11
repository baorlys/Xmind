package sheet.relationship;

import config.Configuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import sheet.node.INode;
import sheet.Sheet;
import util.Pair;

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
    private Pair<INode, INode> relation;
    String name;
    private Configuration configuration;


    public Relationship(Sheet sheet, INode fromNode, INode toNode) {
        this.sheet = sheet;
        this.relation = new Pair<>(fromNode, toNode);
        this.name = Configuration.RELATIONSHIP_NAME;
        this.id = uniqueId.getAndIncrement();
    }

    public static void resetUniqueId() {
        uniqueId.set(0);
    }



}
