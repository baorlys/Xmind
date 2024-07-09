package sheet;

import com.fasterxml.jackson.annotation.*;
import config.Configuration;
import config.PropertiesLoader;
import lombok.Getter;
import lombok.Setter;
import node.INode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Relationship {
    private String id;
    private INode node;
    private List<HashMap<INode,String>> relation;
    @JsonIgnore
    private Configuration configuration;


    public Relationship(INode node) {
        this.node = node;
        this.configuration = new Configuration(PropertiesLoader.load());
        this.relation = new ArrayList<>();
    }

    public void addRelation(INode node) {
        String defaultRelationshipName = configuration.getDefaultRelationshipName();
        HashMap<INode, String> relationMap = new HashMap<>();
        relationMap.put(node, defaultRelationshipName);
        relation.add(relationMap);
    }

    public void changeRelationName(INode node, String relationName) {
        relation.stream().filter(relationMap -> relationMap.containsKey(node))
                .findFirst().ifPresent(relationMap -> relationMap.put(node, relationName));
    }

    public void removeRelation(INode node) {
        relation.stream().filter(relationMap -> relationMap.containsKey(node))
                .findFirst().ifPresent(relation::remove);
    }

    public boolean isRelated(INode node) {
        return relation.stream().anyMatch(relationMap -> relationMap.containsKey(node));
    }


}
