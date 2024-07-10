package export;

import config.ExportStatus;
import sheet.Sheet;

public class ExportPDF implements IExportable {
    @Override
    public ExportResult export(Sheet sheet, String filename) {
        return new ExportResult(ExportStatus.SUCCESS, "Exported to PDF");
    }
}
