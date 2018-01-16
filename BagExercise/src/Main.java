public class Main {
    /**
     * Main method to try the bag for yourself...
     * @param args
     */
    public static void main(String[] args)
    {
        // lets see how the bag array looks like empty:
        System.out.println(System.lineSeparator()+" ----- Initially: -----");
        BagInterface<String> myBag = new ArrayBag<String>(10);
        System.out.println(myBag);
        System.out.println("Full: "+myBag.isFull());
        System.out.println("Empty: "+myBag.isEmpty());

        // adding some elements:
        myBag.add("hello1");
        myBag.add("hello2");
        myBag.add("hello3");
        myBag.add("hello4");
        myBag.add("hello5");
        myBag.add("hello6");
        System.out.println(System.lineSeparator()+" ----- After adding some elements: -----");
        System.out.println(myBag);
        // try removing an element to see what happens to the underlying array:
        System.out.println("Removed: "+myBag.remove());
        System.out.println(myBag);
        System.out.println("Full: "+myBag.isFull());
        System.out.println("Empty: "+myBag.isEmpty());
    }
}
