/**
 * AVLTreeNode.java
 * Implementing the AVL Tree Node
 * Created by Junyi
 * 04/03/2018
 */

public class AVLTreeNode{
    private int key;
    private String value;
    private AVLTreeNode leftChild;
    private AVLTreeNode rightChild;
    private int height;

    // Constructor
    public AVLTreeNode(int key, String value, AVLTreeNode leftChild, AVLTreeNode rightChild) {
        this.key = key;
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.height = 0;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AVLTreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(AVLTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public AVLTreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(AVLTreeNode right) {
        this.rightChild = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * toString function helps print AVL tree node
     */
    @Override
    public String toString() {
        return "AVLTreeNode{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", leftChild=" + leftChild.getKey() +
                ", rightChild=" + rightChild.getKey() +
                ", height=" + height +
                '}';
    }
}
