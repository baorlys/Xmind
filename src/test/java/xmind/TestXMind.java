package xmind;

import export.service.ExportHandler;
import export.ExportStatus;
import export.FileType;
import xmind.file.ImportResult;
import xmind.file.ImportStatus;
import xmind.service.SaveOpenHandle;
import sheet.node.ChildNode;
import sheet.node.Node;
import sheet.node.NodeType;
import sheet.node.Structure;
import sheet.relationship.Relationship;
import sheet.service.RelationshipHandler;
import config.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sheet.Sheet;
import sheet.service.SheetHandler;
import sheet.ViewMode;
import xmind.service.SheetsHandler;
import xmind.service.XMindBuilder;


import static org.junit.jupiter.api.Assertions.*;

class TestXMind {
    XMind xMind;
    Sheet firstSheet;
    @BeforeEach
    void setUp() {
        Sheet.resetUniqueId();
        Node.resetUniqueId();
        Relationship.resetUniqueId();
        xMind = new XMindBuilder()
                .withName("Test XMind")
                .initDefaultTopics()
                .build();
        firstSheet = SheetsHandler.getFirstSheet(xMind);
    }



    @Test
    // Test configuration works
    void testConfig() {
        assertEquals("Central Topic", Configuration.ROOT_TOPIC_NAME.replaceAll("\"", ""));
        assertEquals(4, Integer.parseInt(Configuration.TOPICS_COUNT));
    }

    @Test
    // Test XMind default
    void testDefault() {
        assertEquals("Test XMind", xMind.getName());
        assertEquals(1, SheetsHandler.sheetsCount(xMind));
        assertEquals(4, firstSheet.getRootNode().getChildren().size());
        assertEquals(ViewMode.MIND_MAP, firstSheet.getViewMode());
        assertEquals(Structure.MIND_MAP, firstSheet.getRootNode().getStructure());
    }

    @Test
    // Test parent of main topic
    void testXMindSheetParent() {
        var root = firstSheet.getRootNode();

        ChildNode mainTopic = root.getChildren().get(0);
        assertEquals(root, mainTopic.getParent());
    }

    @Test
    // Test add Sheet to XMind
    void testAddSheet() {
        SheetsHandler.addSheet(xMind, new Sheet(xMind,"Sheet 1"));
        assertEquals(2, SheetsHandler.sheetsCount(xMind));

        SheetsHandler.addSheet(xMind, new Sheet(xMind,"Sheet 2"));
        assertEquals(3, SheetsHandler.sheetsCount(xMind));

    }

    @Test
    // Test get Sheet from XMind
    void testGetSheetById() {
        var sheet1 = new Sheet(xMind,"Sheet 1");
        var sheet2 = new Sheet(xMind,"Sheet 2");

        SheetsHandler.addSheet(xMind, sheet1);
        SheetsHandler.addSheet(xMind, sheet2);
        assertEquals(3, SheetsHandler.sheetsCount(xMind));

        var findSheet1 = SheetsHandler.getSheet(xMind, sheet1.getId());
        assertNotNull(findSheet1);
        assertEquals("Sheet 1", findSheet1.getName());

        Sheet findSheet2 = SheetsHandler.getSheet(xMind, sheet2.getId());
        assertNotNull(findSheet2);
        assertEquals("Sheet 2", findSheet2.getName());

        SheetsHandler.removeSheet(xMind, 2);
        assertNull(SheetsHandler.getSheet(xMind, 2));
    }




    @Test
    // Test remove Sheet from board by id
    void testRemoveSheetById() {
        var sheet = new Sheet(xMind,"Sheet");

        SheetsHandler.addSheet(xMind,sheet);
        assertEquals(2, SheetsHandler.sheetsCount(xMind));

        SheetsHandler.removeSheet(xMind, sheet.getId());
        assertEquals(1, SheetsHandler.sheetsCount(xMind));
    }

    @Test
    // Test remove Sheet from board by Sheet
    void testRemoveSheetBySheet() {
        SheetsHandler.addSheet(xMind, new Sheet(xMind,"Sheet 1"));
        assertEquals(2, SheetsHandler.sheetsCount(xMind));

        var sheet = SheetsHandler.getFirstSheet(xMind);
        SheetsHandler.removeSheet(xMind, sheet);
        assertEquals(1, SheetsHandler.sheetsCount(xMind));
    }


    @Test
        //Test add node to root topic and main topic
    void testAddNode()  {
        var root = firstSheet.getRootNode();

        SheetHandler.addNodeFrom(firstSheet, root);
        var lastNodeAdded = SheetHandler.getLastNodeAdded(firstSheet);
        assertEquals(NodeType.MAIN_TOPIC, lastNodeAdded.getType());

        SheetHandler.addNodeFrom(firstSheet, lastNodeAdded);
        lastNodeAdded = SheetHandler.getLastNodeAdded(firstSheet);
        assertEquals(NodeType.SUB_TOPIC, lastNodeAdded.getType());
    }

    @Test
    // Change topic of node
    void testChangeNodeTopic() {
        var root = firstSheet.getRootNode();

        SheetHandler.changeNodeTopic(root, "New topic");
        assertEquals("New topic", root.getTopic());
    }



    @Test
    // Test get node by id
    void testGetNodeById() {
        var lastNodeAdded = SheetHandler.getLastNodeAdded(firstSheet);
        var nodeFindById = SheetHandler.getNodeById(firstSheet, lastNodeAdded.getId());

        assertNotNull(nodeFindById);
        assertEquals(lastNodeAdded, nodeFindById);
    }

    @Test
    // Test change node to floating topic
    void testChangeNodeToFloatTopic()  {
        var topic = SheetHandler.getLastNodeAdded(firstSheet);
        assertEquals(NodeType.MAIN_TOPIC, topic.getType());

        SheetHandler.toFloatingNode(topic);
        assertEquals(NodeType.FLOATING_TOPIC, topic.getType());
    }

    @Test
    // Test add float topic
    void testInsertFloatTopic()  {
        SheetHandler.insertFloatingNode(firstSheet);
        var floatTopic = SheetHandler.getLastNodeAdded(firstSheet);

        assertEquals(5, SheetHandler.getNodesCount(firstSheet));
        assertEquals(NodeType.FLOATING_TOPIC, floatTopic.getType());
    }

    @Test
    // Test add node to float topic
    void testAddNodeToFloatTopic()  {
        SheetHandler.insertFloatingNode(firstSheet);
        var floatTopic = SheetHandler.getLastNodeAdded(firstSheet);

        SheetHandler.addNodeFrom(firstSheet, floatTopic);
        var newTopic = SheetHandler.getLastNodeAdded(firstSheet);

        assertEquals(NodeType.SUB_TOPIC, newTopic.getType());
    }

    @Test
    // Test remove node
    void testRemoveNode() {
        var root = firstSheet.getRootNode();

        SheetHandler.addNodeFrom(firstSheet, root);
        ChildNode newTopic = SheetHandler.getLastNodeAdded(firstSheet);

        assertEquals(5, SheetHandler.getNodesCount(firstSheet));

        SheetHandler.removeNode(firstSheet, newTopic);

        assertEquals(4, SheetHandler.getNodesCount(firstSheet));
    }

    @Test
    // Test move node to another node
    void testMoveNode() {
        var root = firstSheet.getRootNode();
        var topic = SheetHandler.getLastNodeAdded(firstSheet);

        SheetHandler.addNodeFrom(firstSheet, topic);
        ChildNode newSubTopic = SheetHandler.getLastNodeAdded(firstSheet);

        assertEquals(4, root.getChildren().size());
        assertEquals(1, topic.getChildren().size());

        SheetHandler.moveTo(newSubTopic, root);

        assertEquals(5, root.getChildren().size());
        assertEquals(0, topic.getChildren().size());
    }


    @Test
    // Test create relationship between nodes
    void testCreateRelationship()  {
        var topic1 = SheetHandler.getNodeById(firstSheet, 1);
        var topic2 = SheetHandler.getNodeById(firstSheet, 2);
        var topic3 = SheetHandler.getNodeById(firstSheet, 3);

        RelationshipHandler.createRelation(firstSheet, topic1, topic2);
        RelationshipHandler.createRelation(firstSheet, topic1, topic2);
        RelationshipHandler.createRelation(firstSheet, topic1, topic3);

        assertEquals(3, firstSheet.getRelationships().size());

    }


    @Test
    // Test remove relationship between nodes
    void testRemoveRelationship()  {
        var root = firstSheet.getRootNode();
        var topic = SheetHandler.getLastNodeAdded(firstSheet);

        RelationshipHandler.createRelation(firstSheet, topic, root);

        var rel = firstSheet.getRelationships().get(0);
        assertTrue(RelationshipHandler.isRelationExist(firstSheet, rel.getId()));

        RelationshipHandler.removeRelation(firstSheet, 0);

        assertFalse(RelationshipHandler.isRelationExist(firstSheet, rel.getId()));
    }


    @Test
    // Test change relationship name
    void testChangeRelationshipName()  {
        var root = firstSheet.getRootNode();
        var topic = SheetHandler.getLastNodeAdded(firstSheet);

        RelationshipHandler.createRelation(firstSheet, topic, root);

        RelationshipHandler.changeRelationName(firstSheet, 0, "New name");

        assertEquals("New name", firstSheet.getRelationships().get(0).getName());
    }

    @Test
        // Test export xMind to png file
    void testExportPNG() {
        var result = ExportHandler.export(firstSheet,"filename", FileType.PNG);
        assertEquals(ExportStatus.SUCCESS, result.getStatus());
    }


    @Test
    // Test save xMind to file
    void testSaveXMind() {
        var result = SaveOpenHandle.save(xMind, "filename");
        assertEquals(ExportStatus.SUCCESS, result.getStatus());
    }

    @Test
    // Test open xMind from file
    void testOpenXMind() {
        var result = SaveOpenHandle.save(xMind, "filename");
        assertEquals(ExportStatus.SUCCESS, result.getStatus());

        ImportResult importResult = SaveOpenHandle.open("filename");
        assertEquals(ImportStatus.SUCCESS,importResult.getStatus());
    }



}
