package export;

import sheet.Sheet;

public interface IExportable {
    ExportResult export(Sheet sheet, String filename);
}
