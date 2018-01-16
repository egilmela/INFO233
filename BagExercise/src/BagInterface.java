public interface BagInterface <T> {

    /**
     * Returns the size of the bag
     * @return size of bag
     */
    public int getCurrentSize();

    /**
     * Checks if the bag is empty
     * @return Empty true/false
     */
    public boolean isEmpty();

    /**
     * Adds a new entry to the bag
     * @param object to add
     * @return add successful true/false
     */
    public boolean add(T newEntry);

    /**
     * Removes an unspecified element from the bag
     * @return element removed
     */
    public T remove();

    /**
     * Removes a single occurrence of a given object from the bag
     * @param Entry to remove
     * @return Remove successful true/false
     */
    public boolean remove(T enEntry);

    /**
     * Removes all elements from bag
     */
    public void clear();

    /**
     * Counts the number of occurrences of a given element in bag
     * @param Entry to count
     * @return Number of occurrences
     */
    public int getFrequencyOf(T anEntry);

    /**
     * Checks if bag contains given object
     * @param Object to check for
     * @return  Bag contains object true/false
     */
    public boolean contains(T anEntry);

    /**
     * Converts bag to an array of objects
     * @return
     */
    public T[] toArray();

    /**
     * Checks if the bag is full
     * @return Full true/false
     */
    public boolean isFull();
}
