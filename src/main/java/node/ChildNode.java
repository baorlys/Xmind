package node;

import lombok.Getter;
import lombok.Setter;
import settings.NodeType;
import shape.Point;
import shape.Shape;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ChildNode extends Node implements IChildNode{
    private INode parent;

    public ChildNode(String text, NodeType nodeType) {
        super(text, nodeType);
    }

    @Override
    void initShape() {
        IChildNode lastChild = this.parent.getChildren().stream()
                .reduce((first, second) -> second)
                .orElse(null);
        Shape shape = new Shape(this.getText().length(),
                Optional.ofNullable(lastChild)
                        .map(child -> new Point(child.getShape().getCenter().getX(),
                                child.getShape().getCenter().getY() + 5))
                        .orElse(new Point(parent.getShape().getCenter().getX() + 5,
                                parent.getShape().getCenter().getY())));
        this.setShape(shape);
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

    @Override
    public List<IChildNode> getChildrenOfParent() {
        return this.parent.getChildren();
    }


}
