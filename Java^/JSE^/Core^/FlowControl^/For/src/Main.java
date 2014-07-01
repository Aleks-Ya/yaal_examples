public class Main {
    public static void main(String[] args) {
           breakWithTag();
    }

    /**
     * Переход на метку перед for означает следующий виток цикла.
    */
    private static void breakWithTag() {
        for (int i = 0; i < 5; i++) {
            inner:
            for (int j = 0; j < 3; j++) {
                System.out.println("i=" + i + " j=" + j);
                //if (j > 1) {
                  // System.out.println("Next i");
                   break inner;
                //}
            }
        }
    }
}