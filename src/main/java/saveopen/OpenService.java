package saveopen;

import config.ImportStatus;
import xmind.XMind;

public class OpenService implements IOpenService {
    @Override
    public ImportResult open(String filename) {
        return new ImportResult(ImportStatus.SUCCESS, new XMind());
    }
}
