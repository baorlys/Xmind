package board;

import exceptions.ExceptionOpenFile;
import node.IRootNode;
import settings.PropertiesLoader;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class XMindBuilder {
    Board xMind;
    Logger logger = Logger.getAnonymousLogger();
    PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();

    private final String defaultBoardName = propertiesLoader.getProperty("default.board.name");

    private final int defaultNumberOfMainTopic = Integer.parseInt(propertiesLoader.getProperty("default.number.of.main.topic"));


    public XMindBuilder() throws ExceptionOpenFile {
        xMind = new Board();
    }

    public XMindBuilder initBoard() {
        xMind.setName(defaultBoardName);
        return this;
    }


    public XMindBuilder initDefaultTopics() {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IntStream.range(0, defaultNumberOfMainTopic).forEach(i -> {
            try {
                sheet.addNodeFrom(rootTopic);
            } catch (ExceptionOpenFile e) {
                logger.warning(e.getMessage());
            }
        });
        return this;
    }

    public Board build() {
        return xMind;
    }


    public XMindBuilder initDefaultXMind() {
        return initBoard().initDefaultTopics();
    }

    public XMindBuilder open(String filename) throws IOException, ClassNotFoundException {
        Board.open(filename);
        return this;
    }
}
