package export.filetype;

import export.ExportResult;
import sheet.Sheet;

public interface IExportable {
    ExportResult export(Sheet sheet, String filename);
}
