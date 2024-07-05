package export;

import board.Sheet;
import exceptions.ExceptionExportFile;
import settings.ExportStatus;

public interface ITypeExport {
    ExportStatus export(Sheet sheet, String filename) throws ExceptionExportFile;
}
