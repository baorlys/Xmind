import board.Board;
import board.Sheet;
import board.XMindBuilder;
import exceptions.ExceptionExportFile;
import exceptions.ExceptionOpenFile;
import node.INode;
import shape.Point;
import settings.*;
import node.IChildNode;
import node.IRootNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    void testExportPNG() throws ExceptionExportFile {
        Sheet sheet = xMind.getFirstSheet();
        ExportStatus exportStatus = xMind.export(sheet, FileType.PNG, "filename");
        assertEquals(ExportStatus.SUCCESS, exportStatus);
    }

    @Test
    // Test export xMind to pdf file
    void testExportPDF() throws ExceptionExportFile {
        Sheet sheet = xMind.getFirstSheet();
        ExportStatus exportStatus = xMind.export(sheet, FileType.PDF, "filename");
        assertEquals(ExportStatus.SUCCESS, exportStatus);
    }

    @Test
    // Test export xMind to test
    void testExportTXT() throws ExceptionExportFile {
        Sheet sheet = xMind.getFirstSheet();
        ExportStatus exportStatus = xMind.export(sheet, FileType.TXT, "filename");
        assertEquals(ExportStatus.SUCCESS, exportStatus);
    }

    @Test
    // Test add node to root topic
    void testAddNode() throws ExceptionOpenFile, IOException {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        assertEquals(NodeType.MAIN_TOPIC, sheet.getNode(newMainTopic).getType());
        IChildNode newSubTopic = sheet.addNodeToCurrentTopic(newMainTopic);
        assertEquals(NodeType.SUB_TOPIC, sheet.getNode(newSubTopic).getType());
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
    void testRootNodePosition() {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        assertEquals(960, rootTopic.getShape().getCenter().getX());
        assertEquals(540, rootTopic.getShape().getCenter().getY());
    }


    @Test
    // Test add child node to node by node position
    void testAddNodeByPosition() throws ExceptionOpenFile, IOException {
        Sheet sheet = xMind.getFirstSheet();
        Point positionClick = new Point(960, 540);
        INode nodeInPosition = sheet.getNodeByPosition(positionClick);
        sheet.addNodeToCurrentTopic(nodeInPosition);
        assertEquals(5, nodeInPosition.getChildren().stream().count());
    }

    @Test
    // Test add float topic
    void testAddFloatTopic() throws ExceptionOpenFile, IOException {
        Sheet sheet = xMind.getFirstSheet();
        IChildNode floatTopic = sheet.addFloatTopic();
        assertEquals(NodeType.FLOATING_TOPIC, sheet.getNode(floatTopic).getType());
    }

    @Test
    // Test add node to float topic
    void testAddNodeToFloatTopic() throws ExceptionOpenFile, IOException {
        Sheet sheet = xMind.getFirstSheet();
        IChildNode newFloatTopic = sheet.addFloatTopic();
        IChildNode newSubTopic = sheet.addNodeToCurrentTopic(newFloatTopic);
        assertEquals(NodeType.SUB_TOPIC, sheet.getNode(newSubTopic).getType());
    }

    @Test
    // Test remove node
    void testRemoveNode() throws ExceptionOpenFile, IOException {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic =sheet.addNodeToCurrentTopic(rootTopic);
        assertEquals(6, sheet.getAllNodes().stream().count());
        sheet.removeNode(newMainTopic);
        assertEquals(5, sheet.getAllNodes().stream().count());
    }

    @Test
    // Test move node to another node
    void testMoveNode() throws ExceptionOpenFile, IOException {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        IChildNode newSubTopic = sheet.addNodeToCurrentTopic(newMainTopic);
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
        Point positionCurrentNode = new Point(0, 0);
        Point positionNextNode = new Point(0, 1);
        sheet.moveNode(positionCurrentNode, positionNextNode);
        INode nextNode = sheet.getNodeByPosition(positionNextNode);
        assertEquals(1, nextNode.getChildren().stream().count());
        assertEquals(NodeType.SUB_TOPIC, nextNode.getType());
        INode currentNode = sheet.getNodeByPosition(positionCurrentNode);
        assertEquals(NodeType.SUB_TOPIC, currentNode.getType());
    }

    @Test
    // Test move node to floating topic
    void testMoveNodeToFloatTopic() throws ExceptionOpenFile, IOException {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        assertEquals(5, rootTopic.getChildren().stream().count());
        sheet.moveNode(newMainTopic);
        assertEquals(4, rootTopic.getChildren().stream().count());
        assertEquals(NodeType.FLOATING_TOPIC, newMainTopic.getType());

    }
    @Test
    // Test create relationship between nodes
    void testCreateRelationship() throws ExceptionOpenFile, IOException {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        IChildNode newSubTopic = sheet.addNodeToCurrentTopic(newMainTopic);
        sheet.createRelationship(newSubTopic, rootTopic);
        assertTrue(sheet.getRelationships(newSubTopic).isRelated(rootTopic));
    }


    @Test
    // Test remove relationship between nodes
    void testRemoveRelationship() throws ExceptionOpenFile, IOException {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        sheet.createRelationship(newMainTopic, rootTopic);
        sheet.removeRelationship(newMainTopic, rootTopic);
        assertFalse(sheet.getRelationships(newMainTopic).isRelated(rootTopic));
    }


    @Test
    // Test change relationship name
    void testChangeRelationshipName() throws ExceptionOpenFile, IOException {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IChildNode newMainTopic = sheet.addNodeToCurrentTopic(rootTopic);
        sheet.createRelationship(newMainTopic, rootTopic);
        sheet.changeRelationshipName(newMainTopic, rootTopic, "new relationship name");
        assertTrue(sheet.getRelationships(newMainTopic)
                .getRelation()
                .stream()
                .anyMatch(relationMap -> relationMap.containsValue("new relationship name")));
    }


    @Test
    // Test auto balance map
    void testAutoBalanceMap() throws ExceptionOpenFile, IOException {
        Sheet sheet = xMind.getFirstSheet();
        sheet.addNodeToCurrentTopic(sheet.getRootTopic());
        assertFalse(sheet.isBalanced());
        sheet.balanceMap();
        assertTrue(sheet.isBalanced());
    }

    @Test
    // Test change view mode
    void testChangeViewMode() {
        Sheet sheet = xMind.getFirstSheet();
        sheet.changeViewMode(ViewMode.OUTLINER);
        assertEquals(ViewMode.OUTLINER, sheet.getViewMode());
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
