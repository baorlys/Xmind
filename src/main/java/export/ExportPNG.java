package export;

import board.Sheet;
import settings.ExportStatus;

public class ExportPNG implements IExportable {
    @Override
    public ExportMessage export(Sheet sheet, String filename) {
        return new ExportMessage(ExportStatus.SUCCESS, "Exported to PNG");
    }
}
