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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Getter
@Setter
public class Board implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private transient Logger logger = Logger.getAnonymousLogger();
    private transient List<Sheet> sheets = new ArrayList<>();
    private transient PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
    private final String defaultRootTopicName = propertiesLoader.getProperty("default.root.topic.name");
    private boolean isSaved;

    public Board() throws ExceptionOpenFile {
        this.isSaved = false;
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

    public void removeSheet(int index) {
        sheets.remove(index);
    }

    public void save(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
            this.isSaved = true;
        } catch (IOException e) {
            logger.warning("File not found: " + e.getMessage());
        }
    }

    public static void open(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            in.readObject();
        }
    }
}
