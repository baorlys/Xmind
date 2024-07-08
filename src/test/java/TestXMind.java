import board.Board;
import board.Sheet;
import board.XMindBuilder;
import exceptions.ExceptionOpenFile;
import export.ExportMessage;
import node.INode;
import shape.Point;
import settings.*;
import node.IChildNode;
import node.IRootNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestXMind {
    Board xMind;
    @BeforeEach
    void setUp() throws Exception {
        xMind = new XMindBuilder()
                .initDefaultXMind()
                .build();
    }


    @Test
    // Test if the board has a sheet
    void testXMindSheet() {
        assertEquals(1, xMind.getSheets().stream().count());
    }

    @Test
    // Test sheet have default topics
    void testXMindSheetRootTopic() {
        Sheet sheet = xMind.getFirstSheet();
        assertEquals(4, sheet.getRootTopic().getChildren().stream().count());
    }

    @Test
    // Test parent of main topic
    void testXMindSheetRootTopicParent() {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode mainTopic = rootTopic.getChildren().get(0);
        assertEquals(rootTopic, mainTopic.getParent());
    }

    @Test
    // Test default view mode
    void testXMindSheetRootTopicViewMode() {
        Sheet sheet = xMind.getFirstSheet();
        assertEquals(ViewMode.MIND_MAP, sheet.getViewMode());
    }

    @Test
    // Test all nodes default in sheet
    void testXMindSheetAllNodes() {
        Sheet sheet = xMind.getFirstSheet();
        assertEquals(5, sheet.getAllNodes().stream().count());
    }

    @Test
    // Test default structure
    void testXMindSheetRootTopicStructure() {
        Sheet sheet = xMind.getFirstSheet();
        assertEquals(Structure.MIND_MAP, sheet.getRootTopic().getStructure());
    }


    @Test
    // Test add sheet to board
    void testAddSheet() throws Exception {
        xMind.addSheet();
        assertEquals(2, xMind.getSheets().stream().count());
    }

    @Test
    // Test export xMind to png file
    void testExportPNG() {
        Sheet sheet = xMind.getFirstSheet();
        ExportMessage result = xMind.export(sheet, FileType.PNG, "filename");
        assertEquals(ExportStatus.SUCCESS, result.getStatus());
    }

    @Test
    // Test export xMind to pdf file
    void testExportPDF() {
        Sheet sheet = xMind.getFirstSheet();
        ExportMessage result = xMind.export(sheet, FileType.PDF, "filename");
        assertEquals(ExportStatus.SUCCESS, result.getStatus());
    }

    @Test
    // Test export xMind to test
    void testExportTXT() {
        Sheet sheet = xMind.getFirstSheet();
        ExportMessage result = xMind.export(sheet, FileType.TXT, "filename");
        assertEquals(ExportStatus.SUCCESS, result.getStatus());
        assertEquals("\"Central Topic\"\n" +
                "\tmain topic 1\n" +
                "\tmain topic 2\n" +
                "\tmain topic 3\n" +
                "\tmain topic 4\n", result.getMessage());
    }

    @Test
    // Test add node to root topic
    void testAddNode() throws ExceptionOpenFile {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeFrom(rootTopic);
        assertEquals(NodeType.MAIN_TOPIC, sheet.getNode(newMainTopic).getType());
        IChildNode newSubTopic = sheet.addNodeFrom(newMainTopic);
        assertEquals(NodeType.SUB_TOPIC, sheet.getNode(newSubTopic).getType());
    }

    @Test
    // Change topic of node
    void testChangeNodeTopic() {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        rootTopic.setText("New root topic");
        assertEquals("New root topic", rootTopic.getText());
    }

    @Test
        // Test get node by position
    void testGetNodeByPosition() {
        Sheet sheet = xMind.getFirstSheet();
        Point positionClick = new Point(960, 540);
        INode nodeInPosition = sheet.getNodeByPosition(positionClick);
        assertNotNull(nodeInPosition);
    }

    @Test
    // Test root node position
    void testGetRootNodePosition() {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        assertEquals(960, rootTopic.getShape().getCenter().getX());
        assertEquals(540, rootTopic.getShape().getCenter().getY());
    }

    @Test
    // Test first main topic position
    void testGetFirstMainTopicPosition() {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode firstMainTopic = rootTopic.getChildren().get(0);
        assertEquals(960 + 10, firstMainTopic.getShape().getCenter().getX());
        assertEquals(540, firstMainTopic.getShape().getCenter().getY());
    }


    @Test
    // Test add child node to node by node position
    void testAddNodeByPosition() throws ExceptionOpenFile {
        Sheet sheet = xMind.getFirstSheet();
        Point positionClick = new Point(960, 540);
        INode nodeInPosition = sheet.getNodeByPosition(positionClick);
        sheet.addNodeFrom(nodeInPosition);
        assertEquals(5, nodeInPosition.getChildren().stream().count());
    }

    @Test
    // Test change node to floating topic
    void testChangeNodeToFloatTopic()  {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode mainTopic = rootTopic.getChildren().get(0);
        mainTopic.removeParent();
        assertEquals(NodeType.FLOATING_TOPIC, sheet.getNode(mainTopic).getType());
    }

    @Test
    // Test add float topic
    void testInsertFloatTopic() throws ExceptionOpenFile {
        Sheet sheet = xMind.getFirstSheet();
        IChildNode floatTopic = sheet.insertFloatTopic(new Point(200, 300));
        assertEquals(NodeType.FLOATING_TOPIC, sheet.getNode(floatTopic).getType());
    }

    @Test
    // Test add node to float topic
    void testAddNodeToFloatTopic() throws ExceptionOpenFile {
        Sheet sheet = xMind.getFirstSheet();
        IChildNode newFloatTopic = sheet.insertFloatTopic(new Point(200, 300));
        IChildNode newSubTopic = sheet.addNodeFrom(newFloatTopic);
        assertEquals(NodeType.SUB_TOPIC, sheet.getNode(newSubTopic).getType());
    }

    @Test
    // Test remove node
    void testRemoveNode() throws ExceptionOpenFile{
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic =sheet.addNodeFrom(rootTopic);
        assertEquals(6, sheet.getAllNodes().stream().count());
        sheet.removeNode(newMainTopic);
        assertEquals(5, sheet.getAllNodes().stream().count());
    }

    @Test
    // Test move node to another node
    void testMoveNode() throws ExceptionOpenFile{
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeFrom(rootTopic);
        IChildNode newSubTopic = sheet.addNodeFrom(newMainTopic);
        assertEquals(5, rootTopic.getChildren().stream().count());
        assertEquals(1, newMainTopic.getChildren().stream().count());
        sheet.moveNode(newSubTopic, rootTopic);
        assertEquals(6, rootTopic.getChildren().stream().count());
        assertEquals(0, newMainTopic.getChildren().stream().count());
    }

    @Test
    // Test move node to another node by position
    void testMoveNodeByPosition() {
        Sheet sheet = xMind.getFirstSheet();
        Point positionOfMainTopic1 = sheet.getRootTopic().getChildren().get(0).getShape().getCenter();
        Point positionOfMainTopic2 = sheet.getRootTopic().getChildren().get(1).getShape().getCenter();
        sheet.moveNode(positionOfMainTopic1, positionOfMainTopic2);
        INode mainTopic2 = sheet.getNodeByPosition(positionOfMainTopic2);
        assertEquals(3, sheet.getRootTopic().getChildren().stream().count());
        assertEquals(1, mainTopic2.getChildren().stream().count());
    }

    @Test
    // Test move node to floating topic
    void testMoveNodeToFloatTopic() throws ExceptionOpenFile{
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeFrom(rootTopic);
        assertEquals(5, rootTopic.getChildren().stream().count());
        sheet.moveNode(newMainTopic);
        assertEquals(4, rootTopic.getChildren().stream().count());
        assertEquals(NodeType.FLOATING_TOPIC, newMainTopic.getType());

    }
    @Test
    // Test create relationship between nodes
    void testCreateRelationship() throws ExceptionOpenFile {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeFrom(rootTopic);
        IChildNode newSubTopic = sheet.addNodeFrom(newMainTopic);
        sheet.createRelationship(newSubTopic, rootTopic);
        assertTrue(sheet.getRelationships(newSubTopic).isRelated(rootTopic));
    }


    @Test
    // Test remove relationship between nodes
    void testRemoveRelationship() throws ExceptionOpenFile {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeFrom(rootTopic);
        sheet.createRelationship(newMainTopic, rootTopic);
        sheet.removeRelationship(newMainTopic, rootTopic);
        assertFalse(sheet.getRelationships(newMainTopic).isRelated(rootTopic));
    }


    @Test
    // Test change relationship name
    void testChangeRelationshipName() throws ExceptionOpenFile {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeFrom(rootTopic);
        sheet.createRelationship(newMainTopic, rootTopic);
        sheet.changeRelationshipName(newMainTopic, rootTopic, "new relationship name");
        assertTrue(sheet.getRelationships(newMainTopic)
                .getRelation()
                .stream()
                .anyMatch(relationMap -> relationMap.containsValue("new relationship name")));
    }


    @Test
    // Test auto balance map
    void testAutoBalanceMap() throws ExceptionOpenFile {
        Sheet sheet = xMind.getFirstSheet();
        sheet.addNodeFrom(sheet.getRootTopic());
        assertTrue(sheet.isBalanced());
    }

    @Test
    // Test change view mode
    void testChangeViewMode() {
        Sheet sheet = xMind.getFirstSheet();
        sheet.changeViewMode(ViewMode.OUT_LINER);
        assertEquals(ViewMode.OUT_LINER, sheet.getViewMode());
    }


    @Test
    // Test change structure of node
    void testChangeStructure() {
        Sheet sheet = xMind.getFirstSheet();
        INode node = sheet.getRootTopic();
        sheet.changeStructure(node, Structure.LOGIC_CHART);
        assertEquals(Structure.LOGIC_CHART, node.getStructure());
        assertEquals(Structure.LOGIC_CHART, node.getChildren().get(0).getStructure());

    }




}
