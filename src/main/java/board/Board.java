package board;

import exceptions.ExceptionOpenFile;
import export.ExportFactory;
import export.ExportMessage;
import export.IExportable;
import node.IRootNode;
import node.RootNode;
import settings.FileType;
import lombok.Getter;
import lombok.Setter;
import settings.PropertiesLoader;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Board {
    private String name;
    private List<Sheet> sheets = new ArrayList<>();
    private PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
    private final String defaultRootTopicName = propertiesLoader.getProperty("default.root.topic.name");

    public Board() throws ExceptionOpenFile {
        addSheet();
    }


    public void addSheet() throws ExceptionOpenFile {
        IRootNode rootTopic = new RootNode(defaultRootTopicName);
        int sheetNumber = sheets.size() + 1;
        sheets.add(new Sheet("Map " + sheetNumber, rootTopic));
    }



    public Sheet getFirstSheet() {
        return sheets.get(0);
    }

    public ExportMessage export(Sheet sheet, FileType type, String filename) {
        IExportable export = ExportFactory.getExport(type);
        return export.export(sheet, filename);
    }
}
