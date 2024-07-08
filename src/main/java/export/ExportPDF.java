package export;

import board.Sheet;
import settings.ExportStatus;

public class ExportPDF implements IExportable {
    @Override
    public ExportMessage export(Sheet sheet, String filename) {
        return new ExportMessage(ExportStatus.SUCCESS, "Exported to PDF");
    }
}
