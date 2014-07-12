import java.util.Arrays;
import static java.lang.System.out;

public class Multi {
    public static void main(String[] args) {
        out.println("Второй размер 0");
        Integer[][] arr = new Integer[3][];
        out.println(arrToString(arr));
    }

    private static String arrToString(Object[][] arr) {
      StringBuilder sb = new StringBuilder();
      for(Object[] innerArr : arr) {
        if (arr != null) {
          sb.append(Arrays.deepToString(innerArr));
        } else {
          sb.append("null");
        }
        sb.append("\n");
      }
      return sb.toString();
    }
}