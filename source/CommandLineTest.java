import java.util.Scanner;

/**
 * test.CommandLineTest.java
 * Command line test interface
 * Created by Junyi
 * 04/23/2018
 */

public class CommandLineTest {
    private static AVLTree avlTree = new AVLTree();
    private static SkipList skipList = new SkipList();

    public static void showMenu(String option) {
        if (option.equals("a")) {
            System.out.println("\nWhat do you want?\n"
                    + "(a) Insert an element\n"
                    + "(b) Find an element\n"
                    + "(c) Find the closest key after\n"
                    + "(d) Remove an element\n"
                    + "(e) Print the AVL tree\n"
                    + "(f) Print the AVL tree (Preorder)\n"
                    + "(g) Print the AVL tree (Inorder)\n"
                    + "(h) Print the AVL tree (Postorder)\n"
                    + "(i) Exit");
            System.out.print(">> ");
            avlTreeOption();
        } else {
            System.out.println("\nWhat do you want?\n"
                    + "(a) Insert an element\n"
                    + "(b) Find an element\n"
                    + "(c) Find the closest key after\n"
                    + "(d) Remove an element\n"
                    + "(e) Print the Skip list\n"
                    + "(f) Exit");
            System.out.print(">> ");
            skipListOption();
        }
    }

    public static void avlTreeOption() {
        Scanner input = new Scanner(System.in);
        String option = input.next();
        // Insert
        if (option.equals("a")) {
            System.out.println("InsertElement operation: please input the key(>0):");
            System.out.print(">> ");
            int key = input.nextInt();
            avlTree.insertElement(key, "");
            showMenu("a");
        }
        // Find element
        else if (option.equals("b")) {
            System.out.println("FindElement operation: please input the key:");
            System.out.print(">> ");
            int key = input.nextInt();
            AVLTreeNode avlTreeNode = avlTree.findElement(key);
            if (avlTreeNode != null) {
                System.out.println("We find the element!");
            } else {
                System.out.println("The element does not exist!");
            }
            showMenu("a");
        }
        // Find closest key after
        else if (option.equals("c")) {
            System.out.println("ClosestKeyAfter operation: please input the key:");
            System.out.print(">> ");
            int key = input.nextInt();
            int cka = avlTree.closestKeyAfter(key);
            if (cka != 0) {
                System.out.println("The closet key after the element is " + cka);
            } else {
                System.out.println("The closet key after the element does not exist!");
            }
            showMenu("a");
        }
        // Remove element
        else if (option.equals("d")) {
            System.out.println("RemoveElement operation: please input the key:");
            System.out.print(">> ");
            int key = input.nextInt();
            AVLTreeNode avlTreeNode = avlTree.removeElement(key);
            if (avlTreeNode != null) {
                System.out.println("Element " + avlTreeNode.getKey() + " remove successfully!");
            } else {
                System.out.println("Element do not exist!");
            }
            showMenu("a");
        }
        // Print the AVL Tree
        else if (option.equals("e")) {
            System.out.println(avlTree);
            showMenu("a");
        }
        // Print the AVL Tree(preorder)
        else if (option.equals("f")) {
            if (avlTree.getRoot() != null) {
                avlTree.preorder();
            } else {
                System.out.println("The AVL tree is empty.");
            }
            showMenu("a");
        }
        // Print the AVL Tree(inorder)
        else if (option.equals("g")) {
            if (avlTree.getRoot() != null) {
                avlTree.inorder();
            } else {
                System.out.println("The AVL tree is empty.");
            }
            showMenu("a");
        }
        // Print the AVL Tree(postorder)
        else if (option.equals("h")) {
            if (avlTree.getRoot() != null) {
                avlTree.postorder();
            } else {
                System.out.println("The AVL tree is empty.");
            }
            showMenu("a");
        }
        // Exit
        else if (option.equals("i")) {
            System.exit(0);
        } else {
            System.out.println("Invalid input!");
            showMenu("a");
        }
    }

    public static void skipListOption() {
        Scanner input = new Scanner(System.in);
        String option = input.next();
        // Insert
        if (option.equals("a")) {
            System.out.println("InsertElement operation: please input the key(>0):");
            System.out.print(">> ");
            int key = input.nextInt();
            skipList.insertElement(key, "");
            showMenu("b");
        }
        // Find element
        else if (option.equals("b")) {
            System.out.println("FindElement operation: please input the key:");
            System.out.print(">> ");
            int key = input.nextInt();
            SkipListNode skipListNode = skipList.findElement(key);
            if (skipListNode != null) {
                System.out.println("We find the element!");
            } else {
                System.out.println("The element does not exist!");
            }
            showMenu("b");
        }
        // Find closest key after
        else if (option.equals("c")) {
            System.out.println("ClosestKeyAfter operation: please input the key:");
            System.out.print(">> ");
            int key = input.nextInt();
            int cka = skipList.closestKeyAfter(key);
            if (cka != SkipListNode.END) {
                System.out.println("The closet key after the element is " + cka);
            } else {
                System.out.println("The closet key after the element does not exist!");
            }
            showMenu("b");
        }
        // Remove element
        else if (option.equals("d")) {
            System.out.println("RemoveElement operation: please input the key:");
            System.out.print(">> ");
            int key = input.nextInt();
            SkipListNode skipListNode = skipList.removeElement(key);
            if (skipListNode != null) {
                System.out.println("Element " + skipListNode.getKey() + " remove successfully!");
            } else {
                System.out.println("Element do not exist!");
            }
            showMenu("b");
        }
        // Print the AVL Tree
        else if (option.equals("e")) {
            System.out.println(skipList);
            showMenu("b");
        }
        // Exit
        else if (option.equals("f")) {
            System.exit(0);
        }
        // Invalid input
        else {
            System.out.println("Invalid input!");
            showMenu("b");
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome, user!");
        System.out.println("Which data structure do you want to use?\n" +
                "(a) AVL Tree; (b) Skip List");
        System.out.print(">> ");
        Scanner input = new Scanner(System.in);
        String option = input.next();

        if (option.equals("a")) {
            System.out.println("I have created a empty AVL tree for you.");
            showMenu(option);


        } else if (option.equals("b")) {
            System.out.println("I have created a empty skip list for you.");
            showMenu(option);
        } else {
            System.out.println("Invalid input!");
            return;
        }
    }
}
