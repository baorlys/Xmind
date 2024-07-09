package saveopen;

import export.ExportResult;
import xmind.XMind;

public class ManageSaveOpen {
    ISaveService saveService;
    IOpenService openService;

    public ManageSaveOpen(ISaveService saveService, IOpenService openService) {
        this.saveService = saveService;
        this.openService = openService;
    }

    public ExportResult save(XMind xMind, String filename) {
        return saveService.save(xMind, filename);
    }

    public ImportResult open(String filename) {
        return openService.open(filename);
    }
}
