package export;

import board.Sheet;
import exceptions.ExceptionExportFile;
import node.IChildNode;
import node.INode;
import settings.ExportStatus;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportTXT implements ITypeExport {

    // Functional interface for desktop actions
    @FunctionalInterface
    private interface DesktopAction {
        void perform(File file) throws IOException;
    }

    @Override
    public ExportStatus export(Sheet sheet, String filename) throws ExceptionExportFile {
        String result = dfsRecursive(sheet.getRootTopic());
        // Create a temporary file
        try {
            File tempFile = File.createTempFile(filename, ".txt");
            writeToFile(tempFile, result);
            performDesktopAction(tempFile, this::editFile);
        } catch (IOException e) {
            throw new ExceptionExportFile("Error while exporting to TXT", e);
        }
        return ExportStatus.SUCCESS;
    }

    private void writeToFile(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

    private void performDesktopAction(File file, DesktopAction action) throws IOException {
        try {
            action.perform(file);
        } catch (UnsupportedOperationException | IOException e) {
            throw new IOException("Desktop action failed: " + e.getMessage());
        }
    }

    private void editFile(File file) throws IOException {
        Desktop.getDesktop().edit(file);
    }

    private String dfsRecursive(INode root) {
        StringBuilder sb = new StringBuilder();
        dfs(root, sb, 0);
        return sb.toString();
    }

    private void dfs(INode node, StringBuilder sb, int level) {
        appendNodeWithIndentation(sb, node, level);
        for (IChildNode child : node.getChildren()) {
            dfs(child, sb, level + 1);
        }
    }

    private void appendNodeWithIndentation(StringBuilder sb, INode node, int level) {
        sb.append("\t".repeat(level));
        sb.append(node.getText()).append("\n");
    }
}
