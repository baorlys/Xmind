package export.filetype;

import export.ExportResult;
import export.ExportStatus;
import sheet.node.ChildNode;
import sheet.node.INode;
import sheet.Sheet;


public class ExportTXT implements IExportable {


    @Override
    public ExportResult export(Sheet sheet, String filename) {
        String result = dfsRecursive(sheet.getRootNode());
        return new ExportResult(ExportStatus.SUCCESS, result);
    }


    private String dfsRecursive(INode root) {
        StringBuilder sb = new StringBuilder();
        dfs(root, sb, 0);
        return sb.toString();
    }

    private void dfs(INode node, StringBuilder sb, int level) {
        appendNodeWithIndentation(sb, node, level);
        for (ChildNode child : node.getChildren()) {
            dfs(child, sb, level + 1);
        }
    }

    // No hardcode
    private void appendNodeWithIndentation(StringBuilder sb, INode node, int level) {
        sb.append("\t".repeat(level));
        sb.append(node.getTopic()).append("\n");
    }
}
