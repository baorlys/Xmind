package export;

import xmind.Sheet;

public interface IExportable {
    ExportMessage export(Sheet sheet, String filename);
}
