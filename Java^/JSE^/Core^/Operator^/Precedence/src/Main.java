import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
		orAnd();
		out.println(2 + 2 * 2);
    }
    
    private static void orAnd() {
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		out.println(a++ == 0 || b++ < 0 && c++ < 0 || d++ < 0);
		out.println("a=" + a);
		out.println("b=" + b);
		out.println("c=" + c);
		out.println("d=" + d);
	}
}