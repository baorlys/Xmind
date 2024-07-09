package export;

import config.FileType;
import sheet.ISheet;

import java.util.List;

public interface IExportService {
    ExportResult export(ISheet sheet, String filename, FileType fileType);

    ExportResult exportAll(List<ISheet> sheets, String filename, FileType fileType);
}
