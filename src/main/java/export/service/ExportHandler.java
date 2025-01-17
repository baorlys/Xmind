package export.service;

import export.ExportResult;
import export.FileType;
import export.filetype.IExportable;
import sheet.Sheet;

public class ExportHandler {
    private ExportHandler() {
        throw new IllegalStateException("Utility class");
    }
    public static ExportResult export(Sheet sheet, String filename, FileType fileType) {
        IExportable exporter = ExportFactory.getExport(fileType);
        return exporter.export(sheet, filename);
    }

}
