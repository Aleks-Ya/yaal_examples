import static java.lang.System.out;

public class DeadCode {
    public static void main(String[] args) {
        if (false) {
            out.println("false!");
        }
        out.println("I'm alive!");
    }
}