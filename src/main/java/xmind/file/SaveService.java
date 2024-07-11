package xmind.file;

import export.ExportResult;
import export.ExportStatus;
import xmind.XMind;

public class SaveService implements ISaveService {
    @Override
    public ExportResult save(XMind xMind, String filename) {
        return new ExportResult(ExportStatus.SUCCESS, "Saved successfully");
    }
}
