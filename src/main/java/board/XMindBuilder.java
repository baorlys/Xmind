package board;

import exceptions.ExceptionOpenFile;
import node.IRootNode;
import settings.PropertiesLoader;

import java.io.IOException;
import java.util.stream.IntStream;

public class XMindBuilder {
    Board xmind;

    PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();

    private final String defaultBoardName = propertiesLoader.getProperty("default.board.name");

    private final int defaultNumberOfMainTopic = Integer.parseInt(propertiesLoader.getProperty("default.number.of.main.topic"));


    public XMindBuilder() throws Exception {
        xmind = new Board();
    }

    public XMindBuilder initBoard() {
        xmind.setName(defaultBoardName);
        return this;
    }




    public XMindBuilder initDefaultTopics() {
        Sheet sheet = xmind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IntStream.range(0, defaultNumberOfMainTopic).forEach(i -> {
            try {
                sheet.addNodeToCurrentTopic(rootTopic);
            } catch (ExceptionOpenFile | IOException e) {
                e.printStackTrace();
            }
        });
        return this;
    }

    public Board build() {
        return xmind;
    }


    public XMindBuilder initDefaultXMind() {
        return initBoard().initDefaultTopics();
    }
}
