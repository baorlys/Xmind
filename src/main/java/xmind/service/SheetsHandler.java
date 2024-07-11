package xmind.service;

import sheet.Sheet;
import xmind.XMind;

import java.util.UUID;

public class SheetsHandler {

    private SheetsHandler() {
        throw new IllegalStateException("Utility class");
    }
    public static void addSheet(XMind xMind, Sheet sheet) {
        xMind.getSheets().put(sheet.getId(), sheet);
    }

    public static void removeSheet(XMind xMind, Sheet sheet) {
        xMind.getSheets().remove(sheet.getId());
    }

    public static Sheet getSheet(XMind xMind, UUID id) {
        return xMind.getSheets().get(id);
    }

    public static int sheetsCount(XMind xMind) {
        return xMind.getSheets().size();
    }

    public static Sheet getFirstSheet(XMind xMind) {
        return xMind.getSheets().values().stream().findFirst().orElse(null);
    }

    public static void removeSheet(XMind xMind, UUID id) {
        xMind.getSheets().remove(id);
    }




}
