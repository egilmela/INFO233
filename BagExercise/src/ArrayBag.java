import java.util.Arrays;

public class ArrayBag<T> implements BagInterface<T> {
    private T[] arr; // internal array
    private static final int DEFALT_CAPACITY = 50; // default capacity
    private int size; // current size

    /**
     * Instantiates the bag with default size
     */
    public ArrayBag()
    {
        // call overloaded constructor with default capacity as parameter:
        this(DEFALT_CAPACITY);
    }

    /**
     * Instantiates the bag with given size
     * @param max capacity
     */
    public ArrayBag(int capacity)
    {
        // check size is valid:
        if(capacity < 0)
        {
            throw new NegativeArraySizeException();
        }
        // instantiate internal array:
        this.arr = (T[]) new Object[capacity];
    }

    @Override
    public int getCurrentSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T newEntry) {
        // check if bag is full:
        if( isFull() )
        {
            return false;
        }
        // add element and increment size:
        arr[size]=newEntry;
        size++;
        return true;
    }

    @Override
    public T remove() {
        // use removeelement to remove and return element:
        return removeElement(size-1);
    }

    @Override
    public boolean remove(T anEntry) {
        for (int i=0; i < size; i++)
        {
            // check if current element is equal to anEntry:
            if (anEntry.equals(arr[i]))
            {
                // remove element and return (interrupting the iteration)
                removeElement(i);
                return true;
            }
        }
        // none found:
        return false;
    }

    @Override
    public void clear() {
        // overwrite array with new instance:
        arr = (T[]) new Object[arr.length];
    }

    @Override
    public int getFrequencyOf(T anEntry) {
        int count=0;
        for(T element : arr)
        {
            if ( anEntry.equals(element))
            {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean contains(T anEntry) {
       for(T element : arr)
       {
           if (anEntry.equals(element))
           {
               return true;
           }
       }
       return false;
    }

    @Override
    public T[] toArray() {
        return Arrays.copyOf(arr,arr.length);
    }

    @Override
    public boolean isFull() {
        return size == arr.length;
    }

    /**
     * Removes a given index from the bag
     * @param index to remove
     * @return Object removed, null if none
     */
    private T removeElement(int index)
    {
        // check index valid and bag not empty:
        if (isEmpty() || index < 0)
        {
            return null;
        }
        // extract element at given index:
        T element = arr[index];
        // decrement size:
        size--;
        // overwrite element to remove with last element in bag:
        arr[index] = arr[size];
        arr[size]=null;
        return element;
    }

    /**
     * Override of toString for convenience (not needed...)
     * (NOTE: Normally the toString method would not print the array, but rather a list of all elements in array.
     * Thus this method is only for practice purposes...)
     * @return String representation of bag's internal array
     */
    @Override
    public String toString(){
        return Arrays.toString(arr);
    }
}
