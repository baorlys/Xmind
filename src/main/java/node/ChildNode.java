package node;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import config.NodeType;
import shape.Point;
import shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter

public class ChildNode extends Node implements IChildNode{
    @JsonView(Node.class)
    private INode parent;

    public ChildNode(String text, NodeType nodeType, INode parent)  {
        super(text, nodeType);
        this.parent = parent;
        initShape();
    }

    public ChildNode(String text, NodeType nodeType, Point center) {
        super(text, nodeType);
        this.setShape(new Shape(text.length(), center));
    }



    @Override
    void initShape() {
        IChildNode lastChild = getChildrenOfParent().stream()
                .reduce((first, second) -> second)
                .orElse(null);
        Shape shape = new Shape(this.getText().length(),
                Optional.ofNullable(lastChild)
                        .map(child -> new Point(child.getShape().getCenter().getX(),
                                child.getShape().getCenter().getY() + 10))
                        .orElse(new Point(parent.getShape().getCenter().getX() + 10,
                                parent.getShape().getCenter().getY())));
        this.setShape(shape);
    }

    @Override
    public void moveTo(INode newParent) {
        this.parent.removeChild(this);
        this.parent = newParent;
        NodeType nodeType = AddNodeFactory.getChildNodeType(newParent.getType());
        this.setType(nodeType);
        newParent.addChild(this);
        initShape();
    }

    @Override
    public void removeParent() {
        this.parent.removeChild(this);
        this.parent = null;
        this.setType(NodeType.FLOATING_TOPIC);
    }

    @Override
    public List<IChildNode> getChildrenOfParent() {
        return Optional.ofNullable(this.parent)
                .map(INode::getChildren)
                .orElse(new ArrayList<>());
    }


}
