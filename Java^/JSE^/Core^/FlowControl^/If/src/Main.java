import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        withoutBraces();
        multiWithoutBraces();
    }
 
    /**
     * if-else без скобок.
     */
    private static void withoutBraces() {
        if (1 < 2) 
          for (int i = 0; i < 3; i++) out.println(i);
        else
          for (int i = 9; i > 6; i--) out.println(i);
    }
    
    /**
     * else относится ко 2му if.
     */
    private static void multiWithoutBraces() {
        if (false) out.println("if 1");
        if (true) out.println("if 2");
        else out.println("else");
    }
}