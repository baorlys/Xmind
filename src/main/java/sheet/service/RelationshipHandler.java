package sheet.service;

import sheet.Sheet;
import sheet.node.INode;
import sheet.relationship.Relationship;

import java.util.UUID;


public class RelationshipHandler {
    private RelationshipHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static void createRelation(Sheet sheet, INode fromNode, INode toNode) {
        Relationship rel = new Relationship(sheet, fromNode, toNode);
        sheet.addRelationship(rel);
    }

    public static Relationship getLastRelationAdded(Sheet sheet) {
        return sheet.getRelationships().values().stream().reduce((first, second) -> second).orElse(null);
    }

    public static Relationship getRelation(Sheet sheet, UUID id) {
        return sheet.getRelationships().get(id);
    }

    public static void changeRelationName(Sheet sheet, UUID id, String relationName) {
        sheet.getRelationships().get(id).setName(relationName);
    }

    public static void removeRelation(Sheet sheet, UUID id) {
        sheet.getRelationships().remove(id);
    }


    public static boolean isRelationExist(Sheet sheet, UUID id) {
        return sheet.getRelationships().containsKey(id);
    }



}
