package export;

import sheet.ISheet;

public interface IExportable {
    ExportResult export(ISheet sheet, String filename);
}
