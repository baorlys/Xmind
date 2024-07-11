package xmind.service;

import config.Configuration;
import sheet.Sheet;
import sheet.service.SheetHandler;
import xmind.XMind;

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
        Sheet sheet = new Sheet(xMind, Configuration.SHEET_NAME);
        xMind.getSheets().put(sheet.getId(), sheet);
        IntStream.range(0, Integer.parseInt(Configuration.TOPICS_COUNT))
                .forEach(i -> SheetHandler.addNodeFrom(sheet,sheet.getRootNode()));
        return this;
    }


    public XMind build() {
        return xMind;
    }






}
