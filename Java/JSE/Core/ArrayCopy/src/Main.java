public class Main {
    public static void main(String[] args) {
           int[] bigNums = new int[] {12, 23, 34, 78, 89};
           int[] littleNums = new int[] {1, 2, 3, 4, 5, 6, 7};
           print("bigNums=", bigNums);
           print("littleNums=", littleNums);

           System.arraycopy(bigNums, 2, littleNums, 4, 2);
           print("bigNums=", bigNums);
           print("littleNums=", littleNums);
    }

    private static void print(String prefix, int[] arr) {
            System.out.print(prefix);
            for(int i : arr) {
                    System.out.print(i + " ");
            }
            System.out.println();
    }
}