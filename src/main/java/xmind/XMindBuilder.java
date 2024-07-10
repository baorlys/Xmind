package xmind;

import sheet.Sheet;
import sheet.SheetHandler;

import java.util.stream.IntStream;

public class XMindBuilder {
    XMind xMind;


    public XMindBuilder() {
        xMind = new XMind();
    }



    public XMindBuilder withName(String name) {
        xMind.setName(name);
        return this;
    }



    public XMindBuilder initDefaultTopics() {
        Sheet sheet = new Sheet(xMind,xMind.getConfiguration().getSheetName());
        xMind.getSheets().put(sheet.getId(), sheet);
        IntStream.range(0, Integer.parseInt(xMind.getConfiguration().getTopicCount()))
                .forEach(i -> SheetHandler.addNodeFrom(sheet,sheet.getRootNode()));
        return this;
    }


    public XMind build() {
        return xMind;
    }






}
