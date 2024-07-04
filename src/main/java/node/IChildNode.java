package node;

public interface IChildNode extends INode {
    void moveToParent(INode newParent);
    void removeParent();

    INode getParent();

    void setParent(INode nodeParent);
}
