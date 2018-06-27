/**
 * kipListNode.java
 * Implementing the skip list node
 * Created by Junyi
 * 04/11/2018
 */

public class SkipListNode {
    private int key;
    private String value;
    private SkipListNode predecessor, successor, up, down;

    public final static int START = Integer.MIN_VALUE;
    public final static int END = Integer.MAX_VALUE;

    /**
     * Constructor
     * Initialize the skip list node
     */
    public SkipListNode(int key, String value) {
        this.key = key;
        this.value = value;
        this.up = null;
        this.down = null;
        this.predecessor = null;
        this.successor = null;
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

    public SkipListNode getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(SkipListNode predecessor) {
        this.predecessor = predecessor;
    }

    public SkipListNode getSuccessor() {
        return successor;
    }

    public void setSuccessor(SkipListNode successor) {
        this.successor = successor;
    }

    public SkipListNode getUp() {
        return up;
    }

    public void setUp(SkipListNode up) {
        this.up = up;
    }

    public SkipListNode getDown() {
        return down;
    }

    public void setDown(SkipListNode down) {
        this.down = down;
    }

    /**
     * toString function helps print skip list node
     */
    @Override
    public String toString() {
        return "key-value: " + key + "-" + value;
    }
}