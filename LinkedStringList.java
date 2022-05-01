
import java.util.Iterator;
import java.util.LinkedList;


/**
 * a wrapper-class that has-a LinkedList<String> and delegates methods to it, and have an array
 * of this class instead
 */
public class LinkedStringList implements Iterable {

    /** A linked list that holds strings*/
    private LinkedList<String> list;



    /**
     * Constructs the class with a linked list of strings
     */
    public LinkedStringList(){
        list = new LinkedList<>();
    }



    /**
     * add string to the linked list
     * @param string  the string we want to add
     */
    public void add(String string){
        list.add(string);
    }



    /**
     * check if the linked list contains a given string
     * @param string  the string we want to check
     *
     * @return true if found, false otherwise.
     */
    public boolean contains(String string){
        return this.list.contains(string);
    }



    /**
     * remove a given string from the Linked List
     * @param toDelete  the string we want to delete
     *
     * @return true if operation was successful, false otherwise
     */
    public boolean delete (String toDelete){
        return this.list.remove(toDelete);
    }



    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        return list.iterator();
    }








}
