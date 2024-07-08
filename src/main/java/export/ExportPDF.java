package export;

import xmind.Sheet;
import config.ExportStatus;

public class ExportPDF implements IExportable {
    @Override
    public ExportMessage export(Sheet sheet, String filename) {
        return new ExportMessage(ExportStatus.SUCCESS, "Exported to PDF");
    }
}
