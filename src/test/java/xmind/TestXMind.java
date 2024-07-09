package xmind;

import export.ExportResult;
import export.ExportService;
import export.IExportService;
import node.INode;
import saveopen.ImportResult;
import saveopen.ManageSaveOpen;
import saveopen.OpenService;
import saveopen.SaveService;
import shape.Point;
import config.*;
import node.IChildNode;
import node.IRootNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sheet.ISheet;
import sheet.IManageSheet;
import sheet.ManageSheet;

import static org.junit.jupiter.api.Assertions.*;

class TestXMind {
    XMind xMind;
    ISheet firstSheet;
    @BeforeEach
    void setUp() {
        xMind = new XMindBuilder()
                .withConfigFile("application.properties")
                .setSheetService(new ManageSheet())
                .setExportService(new ExportService())
                .withName("Test XMind")
                .initDefaultTopics()
                .build();
        IManageSheet manageSheet = xMind.getManageSheet();
        firstSheet = manageSheet.getFirstSheet();
    }

    @Test
    // Test XMind has a name
    void testXMindName() {
        assertEquals("Test XMind", xMind.getName());
    }

    @Test
    // Test if the board has a Sheet
    void testXMindSheet() {
        IManageSheet manageSheet = xMind.getManageSheet();
        assertEquals(1, manageSheet.numberOfSheets());
    }

    @Test
    // Test ISheet have default topics
    void testXMindISheetRootTopic() {
        assertEquals(4, firstSheet.getRootNode().getChildren().stream().count());
    }

    @Test
    // Test parent of main topic
    void testXMindISheetRootTopicParent() {
        IRootNode rootTopic = firstSheet.getRootNode();
        IChildNode mainTopic = rootTopic.getChildren().get(0);
        assertEquals(rootTopic, mainTopic.getParent());
    }

    @Test
    // Test default view mode
    void testXMindISheetRootTopicViewMode() {
        assertEquals(ViewMode.MIND_MAP, firstSheet.getViewMode());
    }

    @Test
    // Test all nodes default in Sheet
    void testXMindISheetAllNodes() {
        assertEquals(5, firstSheet.getAllNodes().stream().count());
    }

    @Test
    // Test default structure
    void testXMindISheetRootTopicStructure() {
        assertEquals(Structure.MIND_MAP, firstSheet.getRootNode().getStructure());
    }


    @Test
    // Test add Sheet to board
    void testAddSheet() {
        IManageSheet manageSheet = xMind.getManageSheet();
        manageSheet.addSheet();
        assertEquals(2, manageSheet.numberOfSheets());
    }

    @Test
    // Test remove Sheet from board
    void testRemoveSheet() {
        IManageSheet manageSheet = xMind.getManageSheet();
        manageSheet.addSheet();
        assertEquals(2, manageSheet.numberOfSheets());
        manageSheet.removeSheet(1);
        assertEquals(1, manageSheet.numberOfSheets());
    }

    @Test
    void testRemoveSheetBySheet() {
        IManageSheet manageSheet = xMind.getManageSheet();
        ISheet sheet = manageSheet.getFirstSheet();
        manageSheet.addSheet();
        assertEquals(2, manageSheet.numberOfSheets());
        manageSheet.removeSheet(sheet);
        assertEquals(1, manageSheet.numberOfSheets());
    }

    @Test
    // Test get Sheet by name
    void testGetSheetByName() {
        IManageSheet manageSheet = xMind.getManageSheet();
        ISheet sheet = manageSheet.getFirstSheet();
        assertEquals(sheet, manageSheet.getSheet(sheet.getName()));
    }

    @Test
    // Test add Sheet by Sheet
    void testAddSheetBySheet() {
        IManageSheet manageSheet = xMind.getManageSheet();
        ISheet sheet = manageSheet.getFirstSheet();
        manageSheet.duplicateSheet(sheet);
        assertEquals(2, manageSheet.numberOfSheets());
    }



    @Test
    // Test add node to root topic
    void testAddNode()  {
        IRootNode rootTopic = firstSheet.getRootNode();
        IChildNode newMainTopic = firstSheet.addNodeFrom(rootTopic);
        assertEquals(NodeType.MAIN_TOPIC, newMainTopic.getType());
        IChildNode newSubTopic = firstSheet.addNodeFrom(newMainTopic);
        assertEquals(NodeType.SUB_TOPIC, newSubTopic.getType());
    }

    @Test
    // Change topic of node
    void testChangeNodeTopic() {
        IRootNode rootTopic = firstSheet.getRootNode();
        firstSheet.changeNodeName(rootTopic, "New root topic");
        assertEquals("New root topic", rootTopic.getText());
    }

    @Test
        // Test get node by position
    void testGetNodeByPosition() {
        Point position = new Point(960, 540);
        INode nodeInPosition = firstSheet.getNodeByPosition(position);
        assertNotNull(nodeInPosition);
    }

    @Test
    // Test root node position
    void testGetRootNodePosition() {
        IRootNode rootTopic = firstSheet.getRootNode();
        assertEquals(960, rootTopic.getShape().getCenter().getX());
        assertEquals(540, rootTopic.getShape().getCenter().getY());
    }

    @Test
        // Test first main topic position
    void testGetFirstMainTopicPosition() {
        IRootNode rootTopic = firstSheet.getRootNode();
        IChildNode firstMainTopic = rootTopic.getChildren().get(0);
        assertEquals(960 + 10, firstMainTopic.getShape().getCenter().getX());
        assertEquals(540, firstMainTopic.getShape().getCenter().getY());
    }


    @Test
    // Test add child node to node by node position
    void testAddNodeByPosition()  {
        Point position = new Point(960, 540);
        INode nodeInPosition = firstSheet.getNodeByPosition(position);
        firstSheet.addNodeFrom(nodeInPosition);
        assertEquals(5, nodeInPosition.getChildren().stream().count());
    }

    @Test
    // Test change node to floating topic
    void testChangeNodeToFloatTopic()  {
        IRootNode rootTopic = firstSheet.getRootNode();
        IChildNode mainTopic = rootTopic.getChildren().get(0);
        mainTopic.removeParent();
        assertEquals(NodeType.FLOATING_TOPIC, mainTopic.getType());
    }

    @Test
    // Test add float topic
    void testInsertFloatTopic()  {
        IChildNode floatTopic = firstSheet.insertFloatingNode(new Point(200, 300));
        assertEquals(NodeType.FLOATING_TOPIC, floatTopic.getType());
    }

    @Test
    // Test add node to float topic
    void testAddNodeToFloatTopic()  {
        IChildNode newFloatTopic = firstSheet.insertFloatingNode(new Point(200, 300));
        IChildNode newSubTopic = firstSheet.addNodeFrom(newFloatTopic);
        assertEquals(NodeType.SUB_TOPIC, newSubTopic.getType());
    }

    @Test
    // Test remove node
    void testRemoveNode() {
        IRootNode rootTopic = firstSheet.getRootNode();
        IChildNode newMainTopic =firstSheet.addNodeFrom(rootTopic);
        assertEquals(6, firstSheet.getAllNodes().stream().count());
        firstSheet.removeNode(newMainTopic);
        assertEquals(5, firstSheet.getAllNodes().stream().count());
    }

    @Test
    // Test move node to another node
    void testMoveNode() {
        IRootNode rootTopic = firstSheet.getRootNode();
        IChildNode newMainTopic = firstSheet.addNodeFrom(rootTopic);
        IChildNode newSubTopic = firstSheet.addNodeFrom(newMainTopic);
        assertEquals(5, rootTopic.getChildren().stream().count());
        assertEquals(1, newMainTopic.getChildren().stream().count());
        firstSheet.moveNode(newSubTopic, rootTopic);
        assertEquals(6, rootTopic.getChildren().stream().count());
        assertEquals(0, newMainTopic.getChildren().stream().count());
    }

    @Test
    // Test move node to another node by position
    void testMoveNodeByPosition() {
        Point positionOfMainTopic1 = firstSheet.getRootNode().getChildren().get(0).getShape().getCenter();
        Point positionOfMainTopic2 = firstSheet.getRootNode().getChildren().get(1).getShape().getCenter();
        firstSheet.moveNode(positionOfMainTopic1, positionOfMainTopic2);
        INode mainTopic2 = firstSheet.getNodeByPosition(positionOfMainTopic2);
        assertEquals(3, firstSheet.getRootNode().getChildren().stream().count());
        assertEquals(1, mainTopic2.getChildren().stream().count());
    }

    @Test
    // Test move node to floating topic
    void testMoveNodeToFloatTopic() {
        IRootNode rootTopic = firstSheet.getRootNode();
        IChildNode newMainTopic = firstSheet.addNodeFrom(rootTopic);
        assertEquals(5, rootTopic.getChildren().stream().count());
        firstSheet.moveNode(newMainTopic);
        assertEquals(4, rootTopic.getChildren().stream().count());
        assertEquals(NodeType.FLOATING_TOPIC, newMainTopic.getType());

    }
    @Test
    // Test create relationship between nodes
    void testCreateRelationship()  {
        IRootNode rootTopic = firstSheet.getRootNode();
        IChildNode newMainTopic = firstSheet.addNodeFrom(rootTopic);
        IChildNode newSubTopic = firstSheet.addNodeFrom(newMainTopic);
        firstSheet.createRelationship(newSubTopic, rootTopic);
        assertTrue(firstSheet.getRelationship(newSubTopic).isRelated(rootTopic));
    }


    @Test
    // Test remove relationship between nodes
    void testRemoveRelationship()  {
        IRootNode rootTopic = firstSheet.getRootNode();
        IChildNode newMainTopic = firstSheet.addNodeFrom(rootTopic);
        firstSheet.createRelationship(newMainTopic, rootTopic);
        firstSheet.removeRelationship(newMainTopic, rootTopic);
        assertFalse(firstSheet.isRelated(newMainTopic,rootTopic));
    }


    @Test
    // Test change relationship name
    void testChangeRelationshipName()  {
        IRootNode rootTopic = firstSheet.getRootNode();
        IChildNode newMainTopic = firstSheet.addNodeFrom(rootTopic);
        firstSheet.createRelationship(newMainTopic, rootTopic);
        firstSheet.changeRelationshipName(newMainTopic, rootTopic, "new relationship name");
        assertTrue(firstSheet.getRelationship(newMainTopic)
                .getRelation()
                .stream()
                .anyMatch(relationMap -> relationMap.containsValue("new relationship name")));
    }



    @Test
    // Test change view mode
    void testChangeViewMode() {
        firstSheet.setViewMode(ViewMode.OUT_LINER);
        assertEquals(ViewMode.OUT_LINER, firstSheet.getViewMode());
    }


    @Test
    // Test change structure of node
    void testChangeStructure() {
        INode node = firstSheet.getRootNode();
        node.changeStructure(Structure.LOGIC_CHART);
        assertEquals(Structure.LOGIC_CHART, node.getStructure());
        assertEquals(Structure.LOGIC_CHART, node.getChildren().get(0).getStructure());
    }


    @Test
        // Test export xMind to png file
    void testExportPNG() {
        IExportService manageExport = xMind.getManageExport();
        ExportResult result = manageExport.export(firstSheet,"filename", FileType.PNG);
        assertEquals(ExportStatus.SUCCESS, result.getStatus());
    }

    @Test
        // Test export xMind to test
    void testExportTXT()  {
        IExportService manageExport = xMind.getManageExport();
        firstSheet.addNodeFrom(firstSheet.getRootNode().getChildren().get(0));
        firstSheet.addNodeFrom(firstSheet.getRootNode().getChildren().get(1));
        firstSheet.addNodeFrom(firstSheet.getRootNode().getChildren().get(1));
        ExportResult result = manageExport.export(firstSheet,"filename", FileType.TXT);
        assertEquals(ExportStatus.SUCCESS, result.getStatus());
        assertEquals("\"Central Topic\"\n" +
                "\tmain topic 1\n" +
                "\t\tsub topic 1\n" +
                "\tmain topic 2\n" +
                "\t\tsub topic 1\n" +
                "\t\tsub topic 2\n" +
                "\tmain topic 3\n" +
                "\tmain topic 4\n", result.getMessage());
    }

    @Test
        // Test export all ISheets to png
    void testExportAllPNG() {
        IExportService manageExport = xMind.getManageExport();
        IManageSheet manageSheet = xMind.getManageSheet();
        ExportResult result = manageExport.exportAll(manageSheet.getAllSheets(), "filename", FileType.PNG);
        assertEquals(ExportStatus.SUCCESS, result.getStatus());
    }

    @Test
        // Test export all ISheets to txt
    void testExportAllTXT() {
        IExportService manageExport = xMind.getManageExport();
        IManageSheet manageSheet = xMind.getManageSheet();
        ExportResult result = manageExport.exportAll(manageSheet.getAllSheets(), "filename", FileType.TXT);
        assertEquals(ExportStatus.SUCCESS, result.getStatus());
    }

    @Test
    // Test save xMind to file
    void testSaveXMind() {
        ManageSaveOpen manageSaveOpen = new ManageSaveOpen(new SaveService(), new OpenService());
        ExportResult result = manageSaveOpen.save(xMind, "filename");
        assertEquals(ExportStatus.SUCCESS, result.getStatus());
    }

    @Test
    // Test open xMind from file
    void testOpenXMind() {
        ManageSaveOpen manageSaveOpen = new ManageSaveOpen(new SaveService(), new OpenService());
        ExportResult result = manageSaveOpen.save(xMind, "filename");
        assertEquals(ExportStatus.SUCCESS, result.getStatus());
        ImportResult importResult = manageSaveOpen.open("filename");
        assertEquals(ImportStatus.SUCCESS,importResult.getStatus());
    }



}
