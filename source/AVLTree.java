import java.util.LinkedList;
import java.util.Queue;

/**
 * AVLTree.java
 * Implementing the AVL Tree
 * Created by Junyi
 * 04/03/2018
 */

public class AVLTree implements CommonOperations {

    private AVLTreeNode root;
    // Store the node to be inserted or to be removed
    private AVLTreeNode toReturnNode;

    /**
     * Constructor
     * Initialize the AVL tree
     */
    public AVLTree() {
        this.root = null;
    }

    public AVLTreeNode getRoot() {
        return root;
    }

    /**
     * Get the node's height
     */
    public int getHeight(AVLTreeNode node) {
        if (node != null) {
            return node.getHeight();
        }
        return 0;
    }

    /**
     * Get the size of the AVL subtree
     */
    private int getSize(AVLTreeNode node) {
        if (node == null) {
            return 0;
        } else {
            return (getSize(node.getLeftChild()) + getSize(node.getRightChild()) + 1);
        }
    }

    /**
     * Get the size of the AVL tree
     */
    @Override
    public int getSize() {
        return getSize(root);
    }

    /**
     * Get the greater one
     */
    public int max(int a, int b) {
        return a > b ? a : b;
    }

    /**
     * Single left rotation
     * a's balance factor = +2
     * b's balance factor = +1
     *************************
     * a(BF = +2)            *
     *  \                    *
     *   \                   *
     *    b(BF = +1)         *
     *     \                 *
     *      \                *
     *       b.right(BF = 0) *
     *************************
     */
    private AVLTreeNode singleLeftRotation(AVLTreeNode a) {
        AVLTreeNode b;

        b = a.getRightChild();
        a.setRightChild(b.getLeftChild());
        b.setLeftChild(a);

        a.setHeight(max(getHeight(a.getLeftChild()), getHeight(a.getRightChild())) + 1);
        b.setHeight(max(getHeight(a), getHeight(b.getRightChild())) + 1);

        return b;
    }

    /**
     * Single right rotation
     * a's balance factor = -2
     * b's balance factor = -1
     ********************
     *       a(BF = -2) *
     *      /           *
     *     /            *
     *    b(BF = -1)    *
     *   /              *
     *  /               *
     * b.left(BF = 0)   *
     ********************
     */
    private AVLTreeNode singleRightRotation(AVLTreeNode a) {
        AVLTreeNode b;

        b = a.getLeftChild();
        a.setLeftChild(b.getRightChild());
        b.setRightChild(a);

        a.setHeight(max(getHeight(a.getLeftChild()), getHeight(a.getRightChild())) + 1);
        b.setHeight(max(getHeight(b.getLeftChild()), getHeight(a)) + 1);

        return b;
    }

    /**
     * Left right rotation
     * a's balance factor = -2
     * b's balance factor = +1
     * c's balance factor = 0
     *****************
     *    a(BF = -2) *
     *   /           *
     *  /            *
     * b(BF = +1)    *
     * \             *
     *  \            *
     *   c(BF = 0)   *
     *****************
     */
    private AVLTreeNode leftRightRotation(AVLTreeNode a) {
        a.setLeftChild(singleLeftRotation(a.getLeftChild()));

        return singleRightRotation(a);
    }

    /**
     * Right left rotation
     * a's balance factor = +2
     * b's balance factor = -1
     * c's balance factor = 0
     *****************
     * a(BF = +2)    *
     *  \            *
     *   \           *
     *    b(BF = -1) *
     *   /           *
     *  /            *
     * c(BF = 0)     *
     *****************
     */
    private AVLTreeNode rightLeftRotation(AVLTreeNode a) {
        a.setRightChild(singleRightRotation(a.getRightChild()));

        return singleLeftRotation(a);
    }

    /*
     * Find the node whose key equals to "key"
     * If the node exists, then return the node
     * Else return null
     */
    @Override
    public AVLTreeNode findElement(Integer key) {
        return findElement(root, key);
    }

    /**
     * Recursive findElement
     */
    private AVLTreeNode findElement(AVLTreeNode node, Integer key) {
        // If node does not exist,
        // then return null
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.getKey());
        // Search the left subtree
        if (cmp < 0) {
            return findElement(node.getLeftChild(), key);
        }
        // Search the right subtree
        else if (cmp > 0) {
            return findElement(node.getRightChild(), key);
        }
        // We find the node,
        // then return it
        else {
            return node;
        }
    }

    /*
     * Find the node whose key equals to "key"
     * If the node exists, then return true
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
     * Find the closest key after the "key"
     * If it does not exits, return 0;
     */
    @Override
    public Integer closestKeyAfter(Integer key) {
        // CNA represents the closest node after the node
        AVLTreeNode CNA = closestNodeAfter(root, key);
        if (CNA != null) {
            return CNA.getKey();
        } else {
            return 0;
        }
    }

    /**
     * Find the closest node which key after the "key"
     * If it does not exits, return null;
     */
    @Override
    public AVLTreeNode closestNodeAfter(Integer key) {
        // CNA represents the closest node after the node
        AVLTreeNode CNA = closestNodeAfter(root, key);
        return CNA;
    }

    /**
     * Get the closest node after the node whose key == "key"
     */
    private AVLTreeNode closestNodeAfter(AVLTreeNode node, Integer key) {
        // If the node does not exist,
        // then return null
        if (node == null) {
            return null;
        }

        // CNA represents the closest node after the node
        // CNA will go down from root to the leaf
        // and record the smaller CNA
        AVLTreeNode CNA = null;
        AVLTreeNode temp = null;

        int cmp = key.compareTo(node.getKey());
        // Search the left subtree
        if (cmp < 0) {
            temp = closestNodeAfter(node.getLeftChild(), key);
            // If temp is null, that means the CNA is not in the left subtree
            // We should return the node(temp's parent) because node.key > key
            CNA =  temp != null ? temp : node;
        }
        // Search the right subtree
        else if (cmp > 0) {
            temp = closestNodeAfter(node.getRightChild(), key);
            // If temp is null, that means the CNA is not in the right subtree
            // We should skip the node(temp's parent)
            // return the smallest previous recorded CNA(previous ancestor) if it exists
            CNA =  temp != null ? temp : CNA;
        }
        // We find the node
        else {
            // If the node has right child
            if (node.getRightChild() != null) {
                CNA = node.getRightChild();
            }
            // If the node has not right child
            // Return the CNA record
            else {
                // Do nothing, return the CNA record
            }
        }
        return CNA;
    }

    /**
     * Insert the key-value pair into the AVL tree
     * Return the replicated node with only key and value
     * Its leftChild and rightChild are all "null"
     * If we need know its accurate leftChild and rightChild,
     * then we should use findElement operation
     */
    @Override
    public AVLTreeNode insertElement(Integer key, String value) {
        // toReturnNode stores the node to be inserted(a fake one)
        toReturnNode = null;
        root = insertElement(root, key, value);
        return toReturnNode;
    }

    private AVLTreeNode insertElement(AVLTreeNode node, Integer key, String value) {
        // If the node is null(external), then create a new node object
        if (node == null) {
            node = new AVLTreeNode(key, value, null, null);

            // This is a replicated one of the node to be inserted
            // with both children are null
            toReturnNode = node;
        } else {
            int cmp = key.compareTo(node.getKey());

            // If key > node.key,
            // then insert the node into the left subtree of the node
            if (cmp < 0) {
                node.setLeftChild(insertElement(node.getLeftChild(), key, value));

                // If the AVL tree loses balance after insertion,
                // then re-balance it
                if (getHeight(node.getLeftChild()) - getHeight(node.getRightChild()) == 2) {

                    /**
                     *****************
                     *       node    *
                     *      /        *
                     *     /         *
                     *    node.left  *
                     *   /           *
                     *  /            *
                     * new node(key) *
                     *****************
                     */
                    if (key.compareTo(node.getLeftChild().getKey()) < 0) {
                        node = singleRightRotation(node);
                    }

                    /**
                     *******************
                     *    node         *
                     *   /             *
                     *  /              *
                     * node.left       *
                     * \               *
                     *  \              *
                     *   new node(key) *
                     *******************
                     */
                    else {
                        node = leftRightRotation(node);
                    }
                }
            }

            // If key < node.key,
            // then insert the node into the right subtree of the node
            else if (cmp > 0) {
                node.setRightChild(insertElement(node.getRightChild(), key, value));

                // If the AVL tree loses balance after insertion,
                // then re-balance it
                if (getHeight(node.getRightChild()) - getHeight(node.getLeftChild()) == 2) {

                    /**
                     *********************
                     * node              *
                     * \                 *
                     *  \                *
                     *   node.right      *
                     *   \               *
                     *    \              *
                     *     new node(key) *
                     *********************
                     */
                    if (key.compareTo(node.getRightChild().getKey()) > 0) {
                        node = singleLeftRotation(node);
                    }

                    /**
                     *****************
                     * node          *
                     * \             *
                     *  \            *
                     *   node.right  *
                     *   /           *
                     *  /            *
                     * new node(key) *
                     *****************
                     */
                    else {
                        node = rightLeftRotation(node);
                    }
                }
            }

            // If key == node.key, that means the key
            // has already existed in the AVL tree
            // Report the Error
            else {
                System.out.println("Error: Insert replicated key: " + key);
                return null;
            }
        }
        // Modify the height
        node.setHeight(max(getHeight(node.getLeftChild()), getHeight(node.getRightChild())) + 1);

        return node;
    }

    /**
     * Remove the node whose key == "key"
     * and then return it
     */
    @Override
    public AVLTreeNode removeElement(Integer key) {
        // toReturnNode stores the node to be deleted(a fake one)
        toReturnNode = null;
        root = removeElement(root, key);
        return toReturnNode;
    }

    private AVLTreeNode removeElement(AVLTreeNode node, Integer key) {
        // If the node is external node(null),
        // then return null
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.getKey());

        // if key < node.key,
        // then the node should be in the left subtree
        if (cmp < 0) {
            node.setLeftChild(removeElement(node.getLeftChild(), key));
            // If the AVL tree loses balance after deletion,
            // then re-balance it
            if (getHeight(node.getRightChild()) - getHeight(node.getLeftChild()) == 2) {
                AVLTreeNode right = node.getRightChild();

                /**
                 **************
                 * node       *
                 *  \         *
                 *   \        *
                 *    right   *
                 *   /        *
                 *  /         *
                 * right.left *
                 **************
                 */
                if (getHeight(right.getLeftChild()) > getHeight(right.getRightChild())) {
                    node = rightLeftRotation(node);
                }

                /**
                 *********************
                 * node              *
                 *  \                *
                 *   \               *
                 *    right          *
                 *     \             *
                 *      \            *
                 *       right.right *
                 *********************
                 */
                else {
                    node = singleLeftRotation(node);
                }
            }
        }

        // if key > node.key,
        // then the node should be in the right subtree
        else if (cmp > 0) {
            node.setRightChild(removeElement(node.getRightChild(), key));
            // If the tree is no longer balanced,
            // then re-balance it
            if (getHeight(node.getLeftChild()) - getHeight(node.getRightChild()) == 2) {
                AVLTreeNode left = node.getLeftChild();

                /**
                 *****************
                 *    node       *
                 *   /           *
                 *  /            *
                 * left          *
                 *  \            *
                 *   \           *
                 *    left.right *
                 *****************
                 */
                if (getHeight(left.getRightChild()) > getHeight(left.getLeftChild())) {
                    node = leftRightRotation(node);
                }

                /**
                 **************
                 *       node *
                 *      /     *
                 *     /      *
                 *    left    *
                 *   /        *
                 *  /         *
                 * left.left  *
                 **************
                 */
                else {
                    node = singleRightRotation(node);
                }
            }
        }

        // If We find the node
        else {
            toReturnNode = node;
            // If the to be deleted node has two non-null children
            if ((node.getLeftChild() != null) && (node.getRightChild() != null)) {

                /**
                 * If the left subtree is higher than right subtree,
                 * then 1) find the biggest node of the left subtree
                 *      2) node <- the biggest node
                 *      3) delete the node
                 */
                if (getHeight(node.getLeftChild()) > getHeight(node.getRightChild())) {
                    AVLTreeNode max = maximumNode(node.getLeftChild());
                    node.setKey(max.getKey());
                    node.setLeftChild(removeElement(node.getLeftChild(), max.getKey()));
                }

                /**
                 * If the left subtree is lower than or equals to right subtree,
                 * then 1) find the smallest node of the right subtree
                 *      2) node <- the smallest node
                 *      3) delete the node
                 */
                else {
                    AVLTreeNode min = minimumNode(node.getRightChild());
                    node.setKey(min.getKey());
                    node.setRightChild(removeElement(node.getRightChild(), min.getKey()));
                }
            }
            // If the to be deleted node has only one child or no children
            // if it has only one left child, then replaces the node with his left child and return it
            // if it has only one right child, then replaces the node with his right child and return it
            // If it has no children, then return null
            else {
                node = (node.getLeftChild() != null) ? node.getLeftChild() : node.getRightChild();
            }
        }

        return node;
    }

    /*
     * Get the node with the minimum key of the AVL Tree
     */
    private AVLTreeNode minimumNode(AVLTreeNode node) {
        if (node == null)
            return null;

        while(node.getLeftChild() != null)
            node = node.getLeftChild();
        return node;
    }

    /*
     * Get the node with the maximum key of the AVL Tree
     */
    private AVLTreeNode maximumNode(AVLTreeNode node) {
        if (node == null) {
            return null;
        }

        while(node.getRightChild() != null)
            node = node.getRightChild();
        return node;
    }

    /*
     * Destroy the AVL tree
     */
    @Override
    public void destroy() {
        root = null;
    }

    /**
     * toString function helps print the AVL tree
     */
    @Override
    public String toString() {
        AVLTreeNode node = root;
        String output = "";
        // If the AVL tree is empty
        if (node == null) {
            output += "The AVL tree is empty.";
        }
        // If the AVL tree is not empty
        else {
            output += node.getKey() + " is root.\n";
            Queue<AVLTreeNode> queue = new LinkedList<>();
            // Enqueue
            queue.add(node);
            while (queue.size() > 0) {
                // Dequeue the node
                node = queue.remove();
                // If the left child exists,
                // then enqueue the left child
                if (node.getLeftChild() != null) {
                    AVLTreeNode leftChild = node.getLeftChild();
                    queue.add(leftChild);
                    output += leftChild.getKey()
                            + " is " + node.getKey()
                            + "'s left child.\n";
                }
                // If the right child exists,
                // then enqueue the right child
                if (node.getRightChild() != null) {
                    AVLTreeNode rightChild = node.getRightChild();
                    queue.add(rightChild);
                    output += rightChild.getKey()
                            + " is " + node.getKey()
                            + "'s right child.\n";
                }
            }
        }
        return output;
    }

    /*
     * Preorder traversal
     */
    private void preorder(AVLTreeNode node) {
        if(node != null) {
            System.out.print(node.getKey() + " ");
            preorder(node.getLeftChild());
            preorder(node.getRightChild());
        }
    }

    public void preorder() {
        preorder(root);
    }

    /*
     * Inorder traversal
     */
    private void inorder(AVLTreeNode node) {
        if(node != null)
        {
            inorder(node.getLeftChild());
            System.out.print(node.getKey() + " ");
            inorder(node.getRightChild());
        }
    }

    public void inorder() {
        inorder(root);
    }

    /*
     * Postorder traversal
     */
    private void postorder(AVLTreeNode node) {
        if(node != null) {
            postorder(node.getLeftChild());
            postorder(node.getRightChild());
            System.out.print(node.getKey() + " ");
        }
    }

    public void postorder() {
        postorder(root);
    }
}