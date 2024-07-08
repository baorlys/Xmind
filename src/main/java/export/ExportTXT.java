package export;

import board.Sheet;
import node.IChildNode;
import node.INode;
import settings.ExportStatus;



public class ExportTXT implements IExportable {


    @Override
    public ExportMessage export(Sheet sheet, String filename) {
        String result = dfsRecursive(sheet.getRootTopic());
        return new ExportMessage(ExportStatus.SUCCESS, result);
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
