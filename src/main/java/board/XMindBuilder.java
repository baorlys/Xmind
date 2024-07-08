package board;

import node.ChildNode;
import node.IRootNode;
import node.RootNode;
import settings.NodeType;
import settings.PropertiesLoader;

import java.util.stream.IntStream;

public class XMindBuilder {
    Board xmind;

    PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();

    private final String defaultBoardName = propertiesLoader.getProperty("default.board.name");

    private final String defaultSheetName = propertiesLoader.getProperty("default.sheet.name");
    private final int defaultNumberOfMainTopic = Integer.parseInt(propertiesLoader.getProperty("default.number.of.main.topic"));

    private final String defaultRootTopicName = propertiesLoader.getProperty("default.root.topic.name");

    public XMindBuilder() {
        xmind = new Board();
    }

    public XMindBuilder initBoard() {
        xmind.setName(defaultBoardName);
        return this;
    }

    public XMindBuilder initBoard(String boardName) {
        xmind.setName(boardName);
        return this;
    }

    public XMindBuilder initDefaultSheet() {
        xmind.addSheet(defaultSheetName);
        return this;
    }

    public XMindBuilder initDefaultSheet(String defaultSheetName) {
        xmind.addSheet(defaultSheetName);
        return this;
    }

    public XMindBuilder initDefaultTopics() {
        Sheet sheet = xmind.getFirstSheet();
        sheet.setRootTopic(new RootNode(defaultRootTopicName));
        IRootNode rootTopic = sheet.getRootTopic();
        IntStream.range(0, defaultNumberOfMainTopic)
                .mapToObj(i -> new ChildNode("Main Topic " + i, NodeType.MAIN_TOPIC) {})
                .forEach(rootTopic::addChild);
        return this;
    }

    public Board build() {
        return xmind;
    }


    public XMindBuilder initDefaultXMind() {
        return initBoard().initDefaultSheet().initDefaultTopics();
    }
}
