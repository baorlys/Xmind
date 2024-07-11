package xmind.file;

import export.ExportResult;
import xmind.XMind;

public interface ISaveService {
    ExportResult save(XMind xMind, String filename);
}
