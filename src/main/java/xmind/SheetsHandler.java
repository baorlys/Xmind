package xmind;

import sheet.Sheet;

public class SheetsHandler {

    private SheetsHandler() {
        throw new IllegalStateException("Utility class");
    }
    static void addSheet(XMind xMind, Sheet sheet) {
        xMind.getSheets().put(sheet.getId(), sheet);
    }

    static void removeSheet(XMind xMind, Sheet sheet) {
        xMind.getSheets().remove(sheet.getId());
    }

    static Sheet getSheet(XMind xMind, int id) {
        return xMind.getSheets().get(id);
    }

    static int sheetsCount(XMind xMind) {
        return xMind.getSheets().size();
    }

    static Sheet getFirstSheet(XMind xMind) {
        return xMind.getSheets().values().stream().findFirst().orElse(null);
    }

    static void removeSheet(XMind xMind, int id) {
        xMind.getSheets().remove(id);
    }




}
