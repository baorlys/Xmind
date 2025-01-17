package export;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportResult {
    private ExportStatus status;
    private String message;

    public ExportResult(ExportStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
