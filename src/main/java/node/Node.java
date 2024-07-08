package node;

import lombok.Getter;
import lombok.Setter;
import settings.NodeType;
import settings.PropertiesLoader;
import settings.Structure;
import shape.Point;
import shape.Shape;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Node implements INode {
    PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
    private String text;

    private List<IChildNode> children;

    private Shape shape;

    private NodeType type;

    // Default structure is MIND_MAP
    private Structure structure = Structure.valueOf(propertiesLoader.getProperty("default.structure"));

    protected Node(String text, NodeType type) {
        this.text = text;
        this.children = new ArrayList<>();
        this.type = type;
        initShape();
    }

    public void setText(String text) {
        this.text = text;
        this.shape.setWidth(text.length());
    }

    abstract void initShape();






    @Override
    public void addChild(IChildNode node) {
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
