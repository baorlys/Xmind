package node;

import exceptions.ExceptionOpenFile;
import lombok.Getter;
import lombok.Setter;
import settings.NodeType;
import settings.PropertiesLoader;
import settings.Structure;
import shape.Point;
import shape.Shape;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Node implements INode {
    private PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
    private String text;

    private List<IChildNode> children;

    private Shape shape;

    private NodeType type;

    // Default structure is MIND_MAP
    private Structure structure = Structure.valueOf(propertiesLoader.getProperty("default.structure"));

    protected Node(String text, NodeType type) throws IOException, ExceptionOpenFile {
        this.text = text;
        this.children = new ArrayList<>();
        this.type = type;
        this.shape = new Shape(text.length(), new Point(0,0));
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
