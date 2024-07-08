package xmind;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import config.Configuration;
import export.*;
import node.IRootNode;
import node.RootNode;
import config.FileType;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class XMind {
    private String name;
    @JsonManagedReference
    private List<Sheet> sheets;

    private Configuration configuration;

    private IExportService exportService;

    public XMind() {
        this.sheets = new ArrayList<>();
        this.configuration = new Configuration();
        this.exportService = new ExportService();
        this.name = configuration.getDefaultXMindName();

    }

    public void addSheet()  {
        IRootNode rootTopic = new RootNode(configuration.getDefaultRootTopicName());
        int sheetNumber = sheets.size() + 1;
        sheets.add(new Sheet("Map " + sheetNumber, rootTopic));
    }
    public void addSheet(String sheetName) {
        IRootNode rootTopic = new RootNode(configuration.getDefaultRootTopicName());
        sheets.add(new Sheet(sheetName, rootTopic));
    }

    public Sheet getFirstSheet() {
        return sheets.stream().findFirst().orElse(null);
    }

    public ExportMessage export(Sheet sheet, FileType type, String filename) {
        IExportable export = ExportFactory.getExport(type);
        return export.export(sheet, filename);
    }

    public void removeSheet(int index) {
        sheets.remove(index);
    }


    public ExportMessage exportAll(FileType type, String filename) {
        return exportService.exportAll(sheets, filename,type);
    }

    public ExportMessage exportSheet(int index, FileType type, String filename) {
        return exportService.export(sheets.get(index), filename, type);
    }

    public void save(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.writeValue(new File(filename), this);
    }
    public static XMind open(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return mapper.readValue(new File(filename), XMind.class);
    }
}
