package node;

import lombok.Getter;
import lombok.Setter;
import config.NodeType;
import sheet.Sheet;

@Getter
@Setter

public class ChildNode extends Node implements INode {
    private INode parent;

    public ChildNode(Sheet sheet, String text, NodeType nodeType, INode parent)  {
        super(sheet, text, nodeType);
        this.parent = parent;
    }

    public ChildNode(Sheet sheet, String text, NodeType nodeType) {
        super(sheet, text, nodeType);
    }


    public void moveTo(INode newParent) {
        this.parent.removeChild(this);
        this.parent = newParent;
        NodeType nodeType = AddNodeFactory.getChildNodeType(newParent.getType());
        this.setType(nodeType);
        newParent.addChild(this);
    }

    public void removeParent() {
        this.parent.removeChild(this);
        this.parent = null;
        this.setType(NodeType.FLOATING_TOPIC);
    }

}
