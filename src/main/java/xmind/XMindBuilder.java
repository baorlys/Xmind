package xmind;

import config.Configuration;
import node.IRootNode;

import java.util.stream.IntStream;

public class XMindBuilder {
    XMind xMind;

    Configuration configuration;



    public XMindBuilder() {
        this.configuration = new Configuration();
        xMind = new XMind();
    }

    public XMindBuilder initBoard() {
        String defaultSheetName = configuration.getDefaultSheetName();
        xMind.addSheet(defaultSheetName);
        return this;
    }


    public XMindBuilder initDefaultTopics() {
        Sheet sheet = xMind.getFirstSheet();
        IRootNode rootTopic = sheet.getRootTopic();
        IntStream.range(0, configuration.getDefaultNumberOfMainTopic()).forEach(i -> sheet.addNodeFrom(rootTopic));
        return this;
    }

    public XMind build() {
        return xMind;
    }


    public XMindBuilder initDefaultXMind() {
        return initBoard().initDefaultTopics();
    }


}
