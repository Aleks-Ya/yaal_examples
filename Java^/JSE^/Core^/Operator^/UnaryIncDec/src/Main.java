import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
		int a = 10;
		a = ++a + a + --a - --a + a++;
		System.out.println("a=" + a);
		
		int b = 10;
		b = b++ + b + b-- - b-- + ++b;
		System.out.println("b=" + b);
    }
}