package export;

import sheet.ISheet;
import config.ExportStatus;

public class ExportPNG implements IExportable {
    @Override
    public ExportResult export(ISheet sheet, String filename) {
        return new ExportResult(ExportStatus.SUCCESS, "Exported to PNG");
    }
}
