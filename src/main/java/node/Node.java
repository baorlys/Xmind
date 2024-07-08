package node;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import config.Configuration;
import lombok.Getter;
import lombok.Setter;
import config.NodeType;
import config.Structure;
import shape.Shape;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class Node implements INode {
    private String text;

    @JsonManagedReference
    private List<IChildNode> children;

    private Shape shape;

    private NodeType type;

    private Structure structure;

    private Configuration configuration = new Configuration();
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
