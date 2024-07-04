package export;

import board.Sheet;
import settings.ExportStatus;

public interface ITypeExport {
    ExportStatus export(Sheet sheet, String filename);
}
