import java.util.*;

/**
 * test.PerformanceTest.java
 * Test performance
 * Created by Junyi
 * 04/25/2018
 */

public class PerformanceTest {
    private int[] size = {7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    private int min = 1;
    private int max = 1_000_000;
    private int times = 100_000; // 100_000
    private List<Integer> arr;

    private Map<Integer, Integer> avlInsertElementArr;
    private Map<Integer, Integer> avlFindElementArr;
    private Map<Integer, Integer> avlClosestKeyAfterArr;
    private Map<Integer, Integer> avlRemoveElementArr;

    private Map<Integer, Integer> skipInsertElementArr;
    private Map<Integer, Integer> skipFindElementArr;
    private Map<Integer, Integer> skipClosestKeyAfterArr;
    private Map<Integer, Integer> skipRemoveElementArr;

    public PerformanceTest() {
        this.arr = new ArrayList<>();
        this.avlInsertElementArr = new TreeMap<>();
        this.avlFindElementArr = new TreeMap<>();
        this.avlClosestKeyAfterArr = new TreeMap<>();
        this.avlRemoveElementArr = new TreeMap<>();
        this.skipInsertElementArr = new TreeMap<>();
        this.skipFindElementArr = new TreeMap<>();
        this.skipClosestKeyAfterArr = new TreeMap<>();
        this.skipRemoveElementArr = new TreeMap<>();
    }

    public Map<Integer, Integer> getAvlInsertElementArr() {
        return avlInsertElementArr;
    }

    public Map<Integer, Integer> getAvlFindElementArr() {
        return avlFindElementArr;
    }

    public Map<Integer, Integer> getAvlClosestKeyAfterArr() {
        return avlClosestKeyAfterArr;
    }

    public Map<Integer, Integer> getAvlRemoveElementArr() {
        return avlRemoveElementArr;
    }

    public Map<Integer, Integer> getSkipInsertElementArr() {
        return skipInsertElementArr;
    }

    public Map<Integer, Integer> getSkipFindElementArr() {
        return skipFindElementArr;
    }

    public Map<Integer, Integer> getSkipClosestKeyAfterArr() {
        return skipClosestKeyAfterArr;
    }

    public Map<Integer, Integer> getSkipRemoveElementArr() {
        return skipRemoveElementArr;
    }

    private int randomKey(int min, int max) {
        Random random = new Random();
        int key = random.nextInt((max - min) + 1) + min;

        return key;
    }

    public void calculate() {
        AVLTree avlTree = new AVLTree();
        SkipList skipList = new SkipList();
        long avlInsertTime = 0L;
        long avlFindTime = 0L;
        long avlClosestKeyAfterTime = 0L;
        long avlRemoveTime = 0L;
        long skipInsertTime = 0L;
        long skipFindTime = 0L;
        long skipClosestKeyAfterTime = 0L;
        long skipRemoveTime = 0L;
        int key;
        String value;
        long start;
        long end;

        for (int mSize : size) {
            while (avlTree.getSize() < Math.pow(2, mSize) - 1) {
                key = randomKey(1, max);
                if (!arr.contains(key)) {
                    value = "";
                    avlTree.insertElement(key, value);
                    skipList.insertElement(key, value);
                    arr.add(key);
                }
            }

            int n = 0;
            while (n < times) {
                // Test removeElement
                while (true) {
                    key = randomKey(min, max);
                    if (!arr.contains(key)) {
                        value = "";

                        start = System.nanoTime();
                        avlTree.insertElement(key, value);
                        end = System.nanoTime();
                        avlInsertTime += end - start;

                        start = System.nanoTime();
                        skipList.insertElement(key, value);
                        end = System.nanoTime();
                        skipInsertTime += end - start;

                        arr.add(key);

                        break;
                    }
                }

                // Test findElement
                key = randomKey(min, max);
                start = System.nanoTime();
                avlTree.findElement(key);
                end = System.nanoTime();
                avlFindTime += end - start;

                start = System.nanoTime();
                skipList.findElement(key);
                end = System.nanoTime();
                skipFindTime += end - start;

                // Test closestKeyAfter
                key = randomKey(min, max);
                start = System.nanoTime();
                avlTree.closestKeyAfter(key);
                end = System.nanoTime();
                avlClosestKeyAfterTime += end - start;

                start = System.nanoTime();
                skipList.closestKeyAfter(key);
                end = System.nanoTime();
                skipClosestKeyAfterTime += end - start;

                // Test removeElement
                key = arr.get(randomKey(0, arr.size() - 1));
                start = System.nanoTime();
                avlTree.removeElement(key);
                end = System.nanoTime();

                avlRemoveTime += end - start;

                start = System.nanoTime();
                skipList.removeElement(key);
                end = System.nanoTime();
                skipRemoveTime += end - start;

                arr.remove((Integer) key);

                n++;
            }

            // Get the average time
            avlInsertTime /= times;
            avlFindTime /= times;
            avlClosestKeyAfterTime /= times;
            avlRemoveTime /= times;

            skipInsertTime /= times;
            skipFindTime /= times;
            skipClosestKeyAfterTime /= times;
            skipRemoveTime /= times;

            avlInsertElementArr.put(mSize, (int) avlInsertTime);
            avlFindElementArr.put(mSize, (int) avlFindTime);
            avlClosestKeyAfterArr.put(mSize, (int) avlClosestKeyAfterTime);
            avlRemoveElementArr.put(mSize, (int) avlRemoveTime);

            skipInsertElementArr.put(mSize, (int) skipInsertTime);
            skipFindElementArr.put(mSize, (int) skipFindTime);
            skipClosestKeyAfterArr.put(mSize, (int) skipClosestKeyAfterTime);
            skipRemoveElementArr.put(mSize, (int) skipRemoveTime);

            avlInsertTime = 0L;
            avlFindTime = 0L;
            avlClosestKeyAfterTime = 0L;
            avlRemoveTime = 0L;

            skipInsertTime = 0L;
            skipFindTime = 0L;
            skipClosestKeyAfterTime = 0L;
            skipRemoveTime = 0L;
        }
    }

    public static void main(String[] args) {
        System.out.println("\nThis program may take you 5 minutes to get the result.\n");
        Map<Integer, Integer> avlInsertElementArr;
        Map<Integer, Integer> avlFindElementArr;
        Map<Integer, Integer> avlClosestKeyAfterArr;
        Map<Integer, Integer> avlRemoveElementArr;

        Map<Integer, Integer> skipInsertElementArr;
        Map<Integer, Integer> skipFindElementArr;
        Map<Integer, Integer> skipClosestKeyAfterArr;
        Map<Integer, Integer> skipRemoveElementArr;

        PerformanceTest test = new PerformanceTest();
        test.calculate();

        avlInsertElementArr = test.getAvlInsertElementArr();
        avlFindElementArr = test.getAvlFindElementArr();
        avlClosestKeyAfterArr = test.getAvlClosestKeyAfterArr();
        avlRemoveElementArr = test.getAvlRemoveElementArr();

        skipInsertElementArr = test.getSkipInsertElementArr();
        skipFindElementArr = test.getSkipFindElementArr();
        skipClosestKeyAfterArr = test.getSkipClosestKeyAfterArr();
        skipRemoveElementArr = test.getSkipRemoveElementArr();

        System.out.println("Test condition:");
        System.out.println("Key range: (1, 1_000_000)\n" +
                "Number of times: 100_000 for every size\n");

        System.out.println("AVL Tree performance");
        System.out.println("----------------------------------------"
                + "-------------------------------------------");
        System.out.print("|size       |");
        for (Map.Entry m : avlInsertElementArr.entrySet()) {
            String str = (int) Math.pow(2, (int) m.getKey()) + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }
        System.out.println();
        System.out.print("|log(size)  |");
        for (Map.Entry m : avlInsertElementArr.entrySet()) {
            String str = m.getKey() + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }
        System.out.println();

        System.out.print("|Insert(ns) |");
        for (Map.Entry m : avlInsertElementArr.entrySet()) {
            String str = m.getValue() + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }
        System.out.println();

        System.out.print("|Find(ns)   |");
        for (Map.Entry m : avlFindElementArr.entrySet()) {
            String str = m.getValue() + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }
        System.out.println();

        System.out.print("|Closest(ns)|");
        for (Map.Entry m : avlClosestKeyAfterArr.entrySet()) {
            String str = m.getValue() + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }
        System.out.println();

        System.out.print("|Remove(ns) |");
        for (Map.Entry m : avlRemoveElementArr.entrySet()) {
            String str = m.getValue() + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }

        System.out.println();
        System.out.println("---------------------------------------"
                + "--------------------------------------------");



        System.out.println("Skip List performance");
        System.out.println("---------------------------------------"
                + "--------------------------------------------");
        System.out.print("|size       |");
        for (Map.Entry m : skipInsertElementArr.entrySet()) {
            String str = (int) Math.pow(2, (int) m.getKey()) + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }
        System.out.println();
        System.out.print("|log(size)  |");
        for (Map.Entry m : skipInsertElementArr.entrySet()) {
            String str = m.getKey() + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }
        System.out.println();

        System.out.print("|Insert(ns) |");
        for (Map.Entry m : skipInsertElementArr.entrySet()) {
            String str = m.getValue() + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }
        System.out.println();

        System.out.print("|Find(ns)   |");
        for (Map.Entry m : skipFindElementArr.entrySet()) {
            String str = m.getValue() + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }
        System.out.println();

        System.out.print("|Closest(ns)|");
        for (Map.Entry m : skipClosestKeyAfterArr.entrySet()) {
            String str = m.getValue() + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }
        System.out.println();

        System.out.print("|Remove(ns) |");
        for (Map.Entry m : skipRemoveElementArr.entrySet()) {
            String str = m.getValue() + "|";
            while (str.length() < 7) {
                str = " " + str;
            }
            System.out.print(str);
        }
        System.out.println();
        System.out.println("---------------------------------------"
                + "--------------------------------------------");
        System.out.println();
    }
}
