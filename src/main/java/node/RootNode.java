package node;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import config.Configuration;
import config.NodeType;
import lombok.Getter;
import lombok.Setter;
import shape.Point;
import shape.Shape;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
public class RootNode extends Node implements IRootNode {
    @JsonProperty("id")
    private String id;
    private static final NodeType NODE_TYPE = NodeType.ROOT;
    Configuration configuration;

    public RootNode(String text) {
        super(text,NODE_TYPE);
        this.configuration = new Configuration();
        initShape();
        this.id = generateId();
    }
    private String generateId() {
        // Implement ID generation logic here
        return "root-" + System.currentTimeMillis();
    }

    @Override
    void initShape() {
        int screenWidth = configuration.getDefaultScreenWidth();
        int screenHeight = configuration.getDefaultScreenHeight();
        this.setShape(new Shape(this.getText().length(),new Point(screenWidth/2,screenHeight/2)));
    }
}
