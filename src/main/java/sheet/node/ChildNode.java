package sheet.node;

import lombok.Getter;
import lombok.Setter;
import sheet.Sheet;
import sheet.service.NodeFactory;

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
        this.setType(NodeFactory.getChildNodeType(newParent.getType()));

        newParent.addChild(this);
    }

    public void removeParent() {
        this.parent.removeChild(this);
        this.parent = null;

        this.setType(NodeType.FLOATING_TOPIC);
    }

}
