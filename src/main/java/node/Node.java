package node;

import com.fasterxml.jackson.annotation.*;
import config.Configuration;
import config.PropertiesLoader;
import lombok.Getter;
import lombok.Setter;
import config.NodeType;
import config.Structure;
import shape.Shape;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Node implements INode {
    private String text;

    @JsonView(ChildNode.class)
    private List<IChildNode> children;

    private Shape shape;

    private NodeType type;

    private Structure structure;
    @JsonIgnore
    private Configuration configuration = new Configuration(PropertiesLoader.load());
    protected Node(String text, NodeType type) {
        this.text = text;
        this.children = new ArrayList<>();
        this.type = type;
        this.structure = Structure.valueOf(configuration.getDefaultStructure());
    }

    public void setText(String text) {
        this.text = text;
        this.shape.setWidth(text.length());
    }

    abstract void initShape();






    @Override
    public void addChild(IChildNode node) {
        node.setParent(this);
        this.children.add(node);
    }

    @Override
    public void removeChild(IChildNode node) {
        this.children.remove(node);

    }

    @Override
    public void changeStructure(Structure structure) {
        this.structure = structure;
        children.forEach(child -> child.changeStructure(structure));
    }
}
