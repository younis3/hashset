
/**
 * a hash-set based on chaining. Extends SimpleHashSet.
 * Note: the capacity of a chaining based hash-set is simply the number of buckets (the length of the array
 * of lists).
 */
public class OpenHashSet extends SimpleHashSet {

    /** default initial capacity */
    private final int INITIAL_CAPACITY = 16;

    /** array contains the wrapper-class (each cell in this array is a list of strings) */
    private LinkedStringList [] hashTable;

    /** the current number of elements */
    private int size = 0;


    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet(){
        super(0.75f, 0.25f);
        hashTable = new LinkedStringList[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor  The upper load factor of the hash table
     * @param lowerLoadFactor  The lower load factor of the hash table
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        hashTable = new LinkedStringList[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data  Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data){
        this();
        for (String s: data) {
            this.add(s);
        }
    }



    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        int bucket = clamp(newValue.hashCode());  // the index in the hashtable that will hold this value
        if (hashTable[bucket] == null){
            hashTable[bucket] = new LinkedStringList();  // create a new linked list of strings(wrapper-class)
        }
        if (!hashTable[bucket].contains(newValue)){
            hashTable[bucket].add(newValue);  // if it's not already in the table add it
            this.size += 1;  // increase number of elements by one
            double load = (double)this.size/hashTable.length;  // calculate the load
            if(load > getUpperLoadFactor()) {
                rehash(hashTable.length * 2);  // rehash to a new table (two times as big)
            }
            return true;
        }
        return false;
    }



    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        for (LinkedStringList ls: hashTable) {
            if (ls==null){
                continue;
            }
            if(ls.contains(searchVal)){
                return true;
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
        boolean isDeleted = false;  // flag if deleted or not
        int bucketDel = clamp(toDelete.hashCode()); // index of the wrapper which contains value in the hashtable
        if (hashTable[bucketDel] != null) {
            if (hashTable[bucketDel].delete(toDelete)) {
                isDeleted = true;  // value successfully deleted
                size -= 1;  // decrease number of elements by one
                double load = (double) size / hashTable.length;  // calculate the load
                if (load < getLowerLoadFactor() && hashTable.length > 1) {
                    rehash(hashTable.length / 2);  // rehash to a new table (two times as small)
                }
            }
        }
        return isDeleted;
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
    public int capacity(){
        return hashTable.length;
    }


    /**
     * This method rehashes the table (copy elements to new table) bigger or smaller one depends on the load
     * factor and current number of elements.
     * @param newTableSize  the size of the new rehashed table
     */
    private void rehash(int newTableSize) {
        capacityMinusOne = newTableSize - 1;
        LinkedStringList[] hashTableNew = new LinkedStringList[newTableSize];
        for (LinkedStringList ls: hashTable) {  // for each bucket in the old table
            if (ls == null) {
                continue;
            }
            // for each string in the bucket add it to the new table
            for (Object val : ls) {
                String strVal = val.toString();
                int newBucket = clamp(strVal.hashCode());
                if (hashTableNew[newBucket] == null){
                    hashTableNew[newBucket] = new LinkedStringList();
                }
                hashTableNew[newBucket].add(strVal);
            }
        }
        hashTable = hashTableNew;  // assign hashtable to the new one
    }






}
