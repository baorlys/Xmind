package board;

import exceptions.ExceptionOpenFile;
import lombok.Getter;
import lombok.Setter;
import node.INode;
import settings.PropertiesLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class RelationShip {
    private INode node;

    private List<HashMap<INode,String>> relation;

    private PropertiesLoader propertiesLoader;



    public RelationShip(INode node){
        this.node = node;
        this.relation = new ArrayList<>();
    }

    public void addRelation(INode node) throws ExceptionOpenFile {
        propertiesLoader = PropertiesLoader.getInstance();
        String defaultRelationshipName = propertiesLoader.getProperty("default.relationship.name");
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
