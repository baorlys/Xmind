package relationship;

import node.INode;
import sheet.Sheet;


public class RelationshipHandler {
    private RelationshipHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static void createRelation(Sheet sheet, INode fromNode, INode toNode) {
        Relationship rel = new Relationship(sheet, fromNode, toNode);
        sheet.addRelationship(rel);
    }

    public static void changeRelationName(Sheet sheet, int id, String relationName) {
        sheet.getRelationships().get(id).setName(relationName);
    }

    public static void removeRelation(Sheet sheet, int id) {
        sheet.getRelationships().remove(id);
    }


    public static boolean isRelationExist(Sheet sheet, int id) {
        return sheet.getRelationships().containsKey(id);
    }



}
