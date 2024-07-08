package export;

import config.FileType;
import xmind.Sheet;

import java.util.List;

public interface IExportService {
    ExportMessage export(Sheet sheet, String filename, FileType fileType);

    ExportMessage exportAll(List<Sheet> sheets, String filename, FileType fileType);
}
