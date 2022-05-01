/**
 * an abstract class implementing SimpleSet
 */
public abstract class SimpleHashSet implements SimpleSet {

    /** default initial capacity */
    protected static final int INITIAL_CAPACITY = 16;

    /** the variable that holds the capacity minus one */
    protected int capacityMinusOne = INITIAL_CAPACITY - 1;

    /** The upper load factor of the hash table. */
    private final float upperLoadFactor;

    /** The lower load factor of the hash table. */
    private final float lowerLoadFactor;


    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor  The upper load factor of the hash table.
     * @param lowerLoadFactor  The lower load factor of the hash table
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
    }

    /**
     * The current capacity of the table.
     * @return  The current capacity (number of cells) of the table.
     */
    public abstract int capacity();

    /**
     * clamp an expression to the valid range of table indices
     */
    protected int clamp(int index) {
        return index & capacityMinusOne;
    }

    /**
     * @return  The upper load factor of the hash table
     */
    protected float getUpperLoadFactor() {
        return upperLoadFactor;
    }

    /**
     * @return  The lower load factor of the hash table
     */
    protected float getLowerLoadFactor() {
        return lowerLoadFactor;
    }


}
