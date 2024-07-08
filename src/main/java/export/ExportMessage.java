package export;

import lombok.Getter;
import lombok.Setter;
import config.ExportStatus;

@Getter
@Setter
public class ExportMessage {
    private ExportStatus status;
    private String message;

    public ExportMessage(ExportStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
