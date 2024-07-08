package export;

import config.ExportStatus;
import config.FileType;
import xmind.Sheet;

import java.util.List;

public class ExportService implements IExportService {
    public ExportMessage export(Sheet sheet, String filename, FileType fileType) {
        IExportable exporter = ExportFactory.getExport(fileType);
        return exporter.export(sheet, filename);
    }

    @Override
    public ExportMessage exportAll(List<Sheet> sheets, String filename, FileType fileType) {
        for (Sheet sheet : sheets) {
            IExportable exporter = ExportFactory.getExport(fileType);
            exporter.export(sheet, filename);
        }
        return new ExportMessage(ExportStatus.SUCCESS, "Exported all sheets to " + filename);
    }
}
