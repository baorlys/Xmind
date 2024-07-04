package export;

import board.Sheet;

public interface ITypeExport {
    ExportStatus export(Sheet sheet, String filename);
}
