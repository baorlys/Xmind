package xmind.service;

import export.ExportResult;
import lombok.Setter;
import xmind.XMind;
import xmind.file.*;

import java.util.Optional;

@Setter
public class SaveOpenHandle {
    private static ISaveService saveService = Optional.of(new SaveService()).get();
    private static IOpenService openService = Optional.of(new OpenService()).get();

    //create Manage IO service
    private SaveOpenHandle() {
        throw new IllegalStateException("Utility class");
    }

    public static ExportResult save(XMind xMind, String filename) {
        return saveService.save(xMind, filename);
    }

    public static ImportResult open(String filename) {
        return openService.open(filename);
    }
}
