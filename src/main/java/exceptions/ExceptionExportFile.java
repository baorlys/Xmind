package exceptions;

import java.io.IOException;

public class ExceptionExportFile extends Throwable {

    public ExceptionExportFile(String message, IOException e) {
        super(message, e);
    }
}
