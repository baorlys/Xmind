package export;

import config.ExportStatus;
import config.FileType;
import sheet.ISheet;

import java.util.List;

public class ExportService implements IExportService {
    public ExportResult export(ISheet sheet, String filename, FileType fileType) {
        IExportable exporter = ExportFactory.getExport(fileType);
        return exporter.export(sheet, filename);
    }

    @Override
    public ExportResult exportAll(List<ISheet> sheets, String filename, FileType fileType) {
        for (ISheet sheet : sheets) {
            IExportable exporter = ExportFactory.getExport(fileType);
            exporter.export(sheet, filename);
        }
        return new ExportResult(ExportStatus.SUCCESS, "Exported all sheets to " + filename);
    }
}
