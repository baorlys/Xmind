package export.filetype;

import export.ExportResult;
import export.ExportStatus;
import sheet.Sheet;

public class ExportPDF implements IExportable {
    @Override
    public ExportResult export(Sheet sheet, String filename) {
        return new ExportResult(ExportStatus.SUCCESS, "Exported to PDF");
    }
}
