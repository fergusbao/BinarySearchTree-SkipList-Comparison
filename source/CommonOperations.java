/**
 * CommonOperations.java
 * Four common functions for both AVL tree and skip list
 * Created by Junyi
 * 04/12/2018
 */

public interface CommonOperations<T> {

    /**
     * Insert the key-value pair into
     * the AVL tree or skip list
     * Return the node
     */
    T insertElement(Integer key, String value);

    /**
     * Find the element whose key == "key"
     * If the element exists, then return it
     * Else return null
     */
    T findElement(Integer key);

    /**
     * Find the closest key after the "key"
     */
    Integer closestKeyAfter(Integer key);

    /**
     * Remove the node whose key == "key"
     * and then return it
     */
    T removeElement(Integer key);

    /**
     * Find the element whose key == "key"
     * If the element exists, then return true
     * Else return false
     */
    boolean findElementBoolean(Integer key);

    /**
     * Find the closest node which key after the "key"
     * If it does not exits, return null in AVL tree
     *                       return "end" in skip list
     */
    T closestNodeAfter(Integer key);

    /**
     * Get the size or AVL tree or skip list
     */
    int getSize();

    /**
     * Destroy the AVL tree or skip list
     */
    void destroy();
}
