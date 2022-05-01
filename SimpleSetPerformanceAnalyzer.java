import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * This class has a main method that measures the run-times requested in the “Performance Analysis” section.
 */
public class SimpleSetPerformanceAnalyzer {

    /** The array that holds Data1*/
    private static String[] DATA1 = Ex3Utils.file2array
            ("data/data1.txt");

    /** The array that holds Data2*/
    private static String[] DATA2 = Ex3Utils.file2array
            ("data/data2.txt");

    /** An array that holds the five data structures*/
    private static SimpleSet[] structuresArr = new SimpleSet[5];
    /** An array that holds the five data structures NAMES*/
    private static String[] dataStructuresNames = new String[]{"OpenHashSet", "ClosedHashSet", "TreeSet",
            "LinkedList", "HashSet"};


    /**
     * For each cell in the array decide what is it's data structure it is going to be
     */
    private static void arrayOfDataStructures() {
        structuresArr[0] = new OpenHashSet();
        structuresArr[1] = new ClosedHashSet();
        structuresArr[2] = new CollectionFacadeSet(new TreeSet<String>());
        structuresArr[3] = new CollectionFacadeSet(new LinkedList<String>());
        structuresArr[4] = new CollectionFacadeSet(new HashSet<String>());
    }


    /**
     * The main method that measures the run-times requested in the “Performance Analysis” section.
     */
    public static void main(String[] Args) {

        System.out.println("Starting Test 1.........");
        testBuildData(DATA1, "Data1.txt");

        System.out.println("Starting Test 2.........");
        testBuildData(DATA2, "Data2.txt");

        System.out.println("Starting Test 3.........");
        testContains(DATA1, "Data1.txt", "hi");

        System.out.println("Starting Test 4.........");
        testContains(DATA1, "Data1.txt", "-13170890158");

        System.out.println("Starting Test 5.........");
        testContains(DATA2, "Data2.txt", "23");

        System.out.println("Starting Test 6.........");
        testContains(DATA2, "Data2.txt", "hi");

    }



    /**
     * This method is written ain a way to handle any contain test.
     * @param data  which data file we want to test
     * @param dataName  a string representation of the data we want to test
     * @param contains  what string we want to test
     */
    private static void testContains(java.lang.String[] data,String dataName, String contains){
        arrayOfDataStructures();
        long result;
        for (int i = 0; i < 5; i++) { // for each data structure
            buildData(data, structuresArr[i]);
            if (i == 3) { //linked list (7000 iterations + NO warm up)
                result = analyzeData(structuresArr[i], contains, 7000, false);
            } else {
                result = analyzeData(structuresArr[i], contains, 70000, true);
            }
            System.out.println(dataStructuresNames[i] + " Contains " + "'" + contains + "'" + " In " + dataName
             + ": " + result + "ns.    ");
        }
        System.out.println("");
    }


    /**
     * A helper method to testContains that analyzes and measures the contains test for each given data
     * structure
     * @param dataStructure the data structure we want to test
     * @param contains  what string we want to test
     * @param iterations  number of iterations (i.e 70000 or 7000)
     * @param warmUp  warmp up before or not? (NO warm up for linked list)
     * @return a long number how much time the process took in average
     */
    private static long analyzeData(SimpleSet dataStructure, String contains, int iterations, boolean warmUp) {
        if (warmUp) { // performs a warm up for each data structure unless it's Linked list
            for (int i = 0; i < iterations; i++) {
                dataStructure.contains(contains);
            }
        }
        long timeBeforeContains = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            dataStructure.contains(contains);
        }
        return (System.nanoTime() - timeBeforeContains)/iterations; // divided by num of iterations to get avg
    }


    /**
     * This method is written ain a way to handle any Adding test. and it measures the process time
     * @param data the data we want to add
     * @param dataName the string representation of the data we want to add
     */
    private static void testBuildData(java.lang.String[] data, String dataName){
        arrayOfDataStructures();
        for (int i = 0; i < 5; i++) { //for each data structure
            long timeBeforeAdd = System.nanoTime();
            buildData(data, structuresArr[i]);  // add data to the data structure
            long differenceAdd = System.nanoTime() - timeBeforeAdd;
            System.out.println("initialized " + dataStructuresNames[i] + " with " + dataName + ". Time: " +
                    differenceAdd / 1000000 + "ms.   ");
        }
        System.out.println("");
    }


    /**
     * This method add a givien data to a given data structure
     * @param data  the data we want to add
     * @param dataStructure  the data structure we want to initialize with the given data
     */
    private static void buildData(java.lang.String[] data, SimpleSet dataStructure){
        for (String s : data) { // for each string in data, add it to the given data structure.
            dataStructure.add(s);
        }
    }





}
