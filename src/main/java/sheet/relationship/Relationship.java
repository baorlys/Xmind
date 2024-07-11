package sheet.relationship;

import config.Configuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import sheet.Sheet;
import sheet.node.INode;
import util.Pair;
import java.util.UUID;

@Getter
@Setter
public class Relationship {
    @Setter(AccessLevel.NONE)
    private UUID id;

    private Sheet sheet;
    private Pair<INode, INode> relation;
    String name;
    private Configuration configuration;


    public Relationship(Sheet sheet, INode fromNode, INode toNode) {
        this.id = UUID.randomUUID();
        this.sheet = sheet;
        this.relation = new Pair<>(fromNode, toNode);
        this.name = Configuration.RELATIONSHIP_NAME;
    }




}
