package board;

import exceptions.ExceptionExportFile;
import export.ExportFactory;
import node.IRootNode;
import node.RootNode;
import settings.ExportStatus;
import settings.FileType;
import export.ITypeExport;
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



    public void addSheet() {
        IRootNode rootTopic = new RootNode(defaultRootTopicName);
        int sheetNumber = sheets.size() + 1;
        sheets.add(new Sheet("Map " + sheetNumber, rootTopic));
    }



    public Sheet getFirstSheet() {
        return sheets.get(0);
    }

    public ExportStatus export(Sheet sheet, FileType type, String filename) throws ExceptionExportFile {
        ITypeExport export = ExportFactory.getExport(type);
        return export.export(sheet, filename);
    }
}
