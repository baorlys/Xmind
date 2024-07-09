package saveopen;

import config.ImportStatus;
import lombok.Getter;
import lombok.Setter;
import xmind.XMind;

@Getter
@Setter
public class ImportResult {
    private ImportStatus status;
    private XMind xMind;

    public ImportResult(ImportStatus status, XMind xMind) {
        this.status = status;
        this.xMind = xMind;
    }

}
