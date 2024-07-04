package export;

import board.Sheet;

public class ExportPDF implements ITypeExport {
    @Override
    public ExportStatus export(Sheet sheet, String filename) {
        return ExportStatus.SUCCESS;
    }
}
