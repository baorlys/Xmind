package xmind;

import config.Configuration;
import config.PropertiesLoader;
import export.ExportService;
import node.IRootNode;
import sheet.ISheet;
import sheet.IManageSheet;
import java.util.stream.IntStream;

public class XMindBuilder {
    XMind xMind;

    Configuration configuration;



    public XMindBuilder() {
        this.configuration = new Configuration(PropertiesLoader.load());
        xMind = new XMind();
    }

    public XMindBuilder withConfigFile(String configFile) {
        this.configuration = new Configuration(PropertiesLoader.load(configFile));
        return this;
    }

    public XMindBuilder withName(String name) {
        xMind.setName(name);
        return this;
    }

    public XMindBuilder setSheetService(IManageSheet sheetService) {
        xMind.setSheetService(sheetService);
        return this;
    }

    public XMindBuilder setExportService(ExportService exportService) {
        xMind.setExportService(exportService);
        return this;
    }



    public XMindBuilder initDefaultTopics() {
        ISheet sheet = xMind.getManageSheet().getFirstSheet();
        IRootNode rootTopic = sheet.getRootNode();
        IntStream.range(0, configuration.getDefaultNumberOfMainTopic()).forEach(i -> sheet.addNodeFrom(rootTopic));
        return this;
    }

    public XMind build() {
        return xMind;
    }





}
