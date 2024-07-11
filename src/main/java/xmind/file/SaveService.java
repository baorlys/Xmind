package xmind.file;

import export.ExportStatus;
import export.ExportResult;
import xmind.XMind;

public class SaveService implements ISaveService {
    @Override
    public ExportResult save(XMind xMind, String filename) {
        return new ExportResult(ExportStatus.SUCCESS, "Saved successfully");
    }
}
