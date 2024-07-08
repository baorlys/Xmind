package node;


import java.util.List;

public interface IChildNode extends INode {
    void moveToParent(INode newParent);
    void removeParent();

    List<IChildNode> getChildrenOfParent();

    INode getParent();

    void setParent(INode nodeParent);
}
