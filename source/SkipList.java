import java.util.Random;

/**
 * SkipList.java
 * Implementing the skip list
 * Created by Junyi
 * 04/11/2018
 */

public class SkipList implements CommonOperations {
    private SkipListNode start;
    private SkipListNode end;
    private int size; // the size of the skip list
    private int maxLevel; // the maximum level
    private Random random;

    /**
     * Constructor
     * Initialize the skip list
     */
    public SkipList() {
        start = new SkipListNode(SkipListNode.START, null);
        end = new SkipListNode(SkipListNode.END, null);
        linkHorizontal(start, end);
        maxLevel = 1;
        size = 0;

        random = new Random();
    }

    /**
     * Get the size of the skip list
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Get the maximum level of the skip list
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Insert the key-value pair into the skip list
     * Return the node
     */
    @Override
    public SkipListNode insertElement(Integer key, String value) {
        SkipListNode closestNodeAfter = closestNodeAfter(key);

        // If closestNodeAfter.getPredecessor().getKey() == key,
        // that means the key has already existed in the skip list
        // Report the Error
        if (closestNodeAfter.getPredecessor().getKey() == key) {
            System.out.println("Error: Insert replicated key: " + key);
            return null;
        }

        // newBottomNode is the new node which we will insert in the bottom list
        SkipListNode newBottomNode = new SkipListNode(key, value);
        SkipListNode newNode = newBottomNode;
        // Insert the newBottomNode into the front of the "closestNodeAfter" node
        insertBefore(closestNodeAfter, newBottomNode);
        // We start from bottom level, thus the currentLevel is 1
        int currentLevel = 1;

        // Flip coins
        // random.nextInt return the uniformly distributed
        // integer between 0 (inclusive) and n (exclusive).
        // Therefore, n = 2. We have 50% chance for getting either face of the coin
        // 0 represents the tail of the coin, we stop promoting the node to the upper level list
        // 1 represents the head of the coin, we keep promoting the node to the upper level list
        while (random.nextInt(2) == 1) {
            // Create a new level if necessary
            if (currentLevel >= maxLevel) {
                maxLevel++;
                SkipListNode newStart = new SkipListNode(SkipListNode.START, null);
                SkipListNode newEnd = new SkipListNode(SkipListNode.END, null);
                linkHorizontal(newStart, newEnd);
                linkVertical(newStart, start);
                linkVertical(newEnd, end);
                // Set both start node and end node to the new ones
                start = newStart;
                end = newEnd;
            }

            // Find the successor in the upper level
            while (closestNodeAfter.getUp() == null) {
                closestNodeAfter = closestNodeAfter.getSuccessor();
            }
            closestNodeAfter = closestNodeAfter.getUp();

            // Create a new node for the upper level
            // Only store the key into the node and set value to null
            SkipListNode upperNewNode = new SkipListNode(key, null);
            insertBefore(closestNodeAfter, upperNewNode);
            linkVertical(upperNewNode, newNode);

            newNode = upperNewNode;
            currentLevel++;
        }

        size++;
        // return the new node object in the bottom list
        return newBottomNode;
    }

    /**
     * Find the element whose key == "key"
     * If the element exists, then return it
     * Else return null
     */
    @Override
    public SkipListNode findElement(Integer key) {
        SkipListNode node = start;
        while (true) {
            // Go right
            while (key.compareTo(node.getSuccessor().getKey()) >= 0) {
                node = node.getSuccessor();
            }

            // If we find the node,
            // then go down to the bottom level and return it
            if (node.getKey() == key) {
                while (node.getDown() != null) {
                    node = node.getDown();
                }
                return node;
            }

            // If we do not find the node
            else {
                // If this is not the bottom level,
                // then go down and continue searching
                if (node.getDown() != null) {
                    node = node.getDown();
                }
                // If this is the bottom list
                else {
                    while (key.compareTo(node.getSuccessor().getKey()) >= 0) {
                        node = node.getSuccessor();
                    }
                    // If we find the node,
                    // then return it
                    if (key.compareTo(node.getKey()) == 0) {
                        return node;
                    }
                    // If we do not find the node,
                    // then return null
                    else {
                        return null;
                    }
                }
            }
        }
    }

    /**
     * Find the node whose key is the closest key after the "key"
     */
    @Override
    public Integer closestKeyAfter(Integer key) {
        return closestNodeAfter(key).getKey();
    }

    /**
     * Find the closest node which key after the "key"
     * If it does not exits, return node "end" in the bottom level
     */
    @Override
    public SkipListNode closestNodeAfter(Integer key) {
        SkipListNode node = start;
        while (true) {
            // Go right
            while (key.compareTo(node.getSuccessor().getKey()) >= 0) {
                node = node.getSuccessor();
            }

            // If we find the node
            if (node.getKey() == key) {
                // Go down to the bottom level
                while (node.getDown() != null) {
                    node = node.getDown();
                }
                // Return its successor
                return node.getSuccessor();
            }

            // If we find the closest node before the node with key == "key" in current level
            else {
                // Go down (only once), keep doing the while loop
                if (node.getDown() != null) {
                    node = node.getDown();
                }
                // We get the bottom list
                else {
                    // Find the "closest node after" node
                    while (key.compareTo(node.getSuccessor().getKey()) >= 0) {
                        node = node.getSuccessor();
                    }
                    return node.getSuccessor();
                }
            }
        }
    }

    /**
     * Remove the node whose key == "key"
     * and then return it
     */
    public SkipListNode removeElement(Integer key) {
        SkipListNode toRemoveNode = findElement(key);
        // If the node does not exists, then return null
        if (toRemoveNode == null) {
            return null;
        }

        // Delete the bottom level node
        SkipListNode node = toRemoveNode;
        linkHorizontal(node.getPredecessor(), node.getSuccessor());
        // Delete the upper level nodes
        while (node.getUp() != null) {
            node = node.getUp();
            linkHorizontal(node.getPredecessor(), node.getSuccessor());
        }

        // Remove unnecessary upper level
        while (start.getSuccessor() == end) {
            if (maxLevel == 1) {
                break;
            }
            start = start.getDown();
            start.setUp(null);
            end = end.getDown();
            end.setUp(null);
            maxLevel--;
        }

        size--;
        return toRemoveNode;
    }

    /**
     * Find the Element
     * If we find the node, then return true
     * Else return false
     */
    @Override
    public boolean findElementBoolean(Integer key) {
        if (findElement(key) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Insert newNode just in front of the node
     */
    private void insertBefore(SkipListNode node, SkipListNode newNode) {
        linkHorizontal(node.getPredecessor(), newNode);
        linkHorizontal(newNode, node);
    }

    /**
     * frontNode is in front of rearNode
     * Connect the link between frontNode and rearNode
     * [..., frontNode, rearNode, ...]
     */
    private void linkHorizontal(SkipListNode frontNode, SkipListNode rearNode) {
        frontNode.setSuccessor(rearNode);
        rearNode.setPredecessor(frontNode);
    }

    /**
     * upNode is in the upper level list
     * rather downNode is the node with the same key in the lower level list
     * Connect the link between upNode and downNode
     * [...,  upNode,  ...]
     * [..., downNode, ...]
     */
    private void linkVertical(SkipListNode upNode, SkipListNode downNode) {
        upNode.setDown(downNode);
        downNode.setUp(upNode);
    }

    /*
     * Destroy the skip list
     */
    @Override
    public void destroy() {
         start = null;
         end = null;
    }

    /**
     * toString function helps print skip list
     */
    @Override
    public String toString() {
        if (getSize() == 0) {
            return "THe skip list is empty.\n";
        }

        String output = "";
        SkipListNode startNode = start;
        SkipListNode node = startNode.getSuccessor();
        for (int i = maxLevel; i > 0; i--) {
            output += "Level " + i + ": start-";
            while (node.getKey() != SkipListNode.END) {
                output += node.getKey() + "-";
                node = node.getSuccessor();
            }
            output += "end\n";
            startNode = startNode.getDown();
            if (startNode != null) {
                node = startNode.getSuccessor();
            }
        }
        return output;
    }
}