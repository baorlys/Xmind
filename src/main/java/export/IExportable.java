package export;

import board.Sheet;

public interface IExportable {
    ExportMessage export(Sheet sheet, String filename);
}
