package node;

import lombok.Getter;
import lombok.Setter;
import settings.NodeType;

@Getter
@Setter
public class ChildNode extends Node implements IChildNode{
    private INode parent;

    public ChildNode(String text, NodeType nodeType) {
        super(text, nodeType);
    }

    @Override
    public void moveToParent(INode newParent) {
        this.parent.removeChild(this);
        this.parent = newParent;
        NodeType nodeType = AddNodeFactory.getChildNodeType(newParent.getType());
        this.setType(nodeType);
        newParent.addChild(this);
    }

    @Override
    public void removeParent() {
        this.parent.removeChild(this);
        this.parent = null;
        this.setType(NodeType.FLOATING_TOPIC);
    }

}
