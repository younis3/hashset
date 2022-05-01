
/**
 * a hash-set based on closed-hashing with quadratic probing.
 */
public class ClosedHashSet extends SimpleHashSet {

    /** Flag to replace a deleted value */
    private final String DELETED_FLAG = "";

    /** default initial capacity */
    private final int INITIAL_CAPACITY = 16;

    /** the closed table */
    private String [] closedTable;

    /** the current number of elements */
    private int size = 0;


    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet(){
        super(0.75f, 0.25f);
        closedTable = new String[INITIAL_CAPACITY];
    }


    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor  The upper load factor of the hash table
     * @param lowerLoadFactor  The lower load factor of the hash table
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        closedTable = new String[INITIAL_CAPACITY];
    }


    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     * @param data  Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data){
        this();
        for (String s: data) {
            this.add(s);
        }
    }


    /** calculate the index of the value in the table by the quadratic probing */
    private int getIndex(String string, int i){
        return (string.hashCode() + (i * i + i) / 2) & capacityMinusOne;
    }


    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if (this.contains(newValue)){  // to not allow duplicates
            return false;
        }
        double load = (double)(this.size+1)/closedTable.length;  // calculate the load
        if(load > getUpperLoadFactor()) {
            rehash(closedTable.length * 2);   // rehash to a new table (two times as big)
        }
        int i = 0;
        while (true) {
            int index = clamp(getIndex(newValue, i));  // the index in the hashtable that will hold this value
            if (closedTable[index] == null) {
                closedTable[index] = newValue;
                size++;  // increase number of elements by one
                return true;
            }
            i++;  // if the cell is not empty try another i (in the quadratic probing)
        }
    }


    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        /* (I tried to make it faster by using hashcode and index but still takes a lot more time
         than this! I guess I am missing something)) */


        for(int i=0; i<closedTable.length; i++){
            if(closedTable[i] != null){
                if(closedTable[i].equals(searchVal)){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        for (int i=0; i<closedTable.length; i++) {
            if (closedTable[i] != null) {
                if (closedTable[i].equals(toDelete)) {  // if value to delete was found
                    closedTable[i] = DELETED_FLAG;  // flag a cell as deleted
                    size -= 1;  // decrease number of elements by one
                    double load = (double) size / closedTable.length;  // calculate the load
                    if (load < getLowerLoadFactor() && closedTable.length > 1) {
                        rehash(closedTable.length / 2);  // rehash to a new table (two times as small)
                    }
                    return true;  // operation was successful
                }
            }
        }
        return false;
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return this.size;
    }


    /**
     * The current capacity of the table.
     * @return  The current capacity (number of cells) of the table.
     */
    @Override
    public int capacity() {
        return closedTable.length;
    }


    /**
     * This method rehashes the table (copy elements to new table) bigger or smaller one depends on the load
     * factor and current number of elements.
     * @param newTableSize  the size of the new rehashed table
     */
    private void rehash(int newTableSize){
        capacityMinusOne = newTableSize - 1;
        String [] closedTableOld = closedTable;  // holds the old hashtable
        closedTable = new String[newTableSize];  // make a new hashtable with the new size
        for (String s: closedTableOld) {  // for each string in the old table
            if (s == null) {
                continue;  // if found a null value in the old table skip it
            }
            int i = 0;
            while (true) {
                int index = clamp(getIndex(s, i));  // calculate the index (quadratic probing)
                if (closedTable[index] == null) {
                    closedTable[index] = s;  // add value to the new table
                    break;  // quit quadratic probing for this value
                }
                i++;  // if the cell is not empty try another i (in the quadratic probing)
            }
        }
    }






}
