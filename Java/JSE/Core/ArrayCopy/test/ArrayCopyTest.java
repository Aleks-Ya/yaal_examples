import org.junit.Test;

public class ArrayCopyTest {
       private final int[] bigNums = new int[] {12, 23, 34, 78, 89};
       private final int[] littleNums = new int[] {1, 2, 3, 4, 5, 6, 7};


    @Test
    public void normal() {
           print("bigNums=", bigNums);
           print("littleNums=", littleNums);

           System.arraycopy(bigNums, 2, littleNums, 4, 2);
           print("bigNums=", bigNums);
           print("littleNums=", littleNums);
    }

    @Test(expected = NullPointerException.class)
    public void npe() {
           System.arraycopy(null, 1, littleNums, 1, 1);
    }

    @Test(expected = ArrayStoreException.class)
    public void arrayStoreException() {
           Integer[] intArray = new Integer[] {1, 2, 3, 4, 5};
           System.arraycopy(bigNums, 1, intArray, 1, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void indexOutOfBoundsException() {
           System.arraycopy(bigNums, 2, littleNums, 4, 20);
    }

    private static void print(String prefix, int[] arr) {
            System.out.print(prefix);
            for(int i : arr) {
                    System.out.print(i + " ");
            }
            System.out.println();
    }

}