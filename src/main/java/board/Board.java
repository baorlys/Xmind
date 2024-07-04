package board;

import export.ExportFactory;
import settings.ExportStatus;
import settings.FileType;
import export.ITypeExport;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Board {
    private String name;
    private List<Sheet> sheets = new ArrayList<>();



    public void addSheet() {
        int sheetNumber = sheets.size() + 1;
        sheets.add(new Sheet("Map " + sheetNumber));
    }

    public void addSheet(String sheetName) {
        sheets.add(new Sheet(sheetName));
    }

    public Sheet getFirstSheet() {
        return sheets.get(0);
    }

    public ExportStatus export(Sheet sheet, FileType type, String filename) {
        ITypeExport export = ExportFactory.getExport(type);
        return export.export(sheet, filename);
    }
}
