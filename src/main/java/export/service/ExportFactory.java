package export.service;

import export.FileType;
import export.filetype.IExportable;
import export.filetype.ExportPDF;
import export.filetype.ExportPNG;
import export.filetype.ExportTXT;

import java.util.EnumMap;
import java.util.Map;

public class ExportFactory {
    private static final Map<FileType, IExportable> exports = new EnumMap<>(FileType.class);
    private ExportFactory() {

    }


    static {
        exports.put(FileType.PNG, new ExportPNG());
        exports.put(FileType.PDF, new ExportPDF());
        exports.put(FileType.TXT, new ExportTXT());
    }

    public static IExportable getExport(FileType type) {
        return exports.get(type);
    }
}
