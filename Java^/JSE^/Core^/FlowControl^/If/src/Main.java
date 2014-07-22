import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
		withoutBraces();
    }
 
	/**
	 * if-else без скобок.
	 */
    private static void withoutBraces() {
		if (1 < 2) 
			for (int i = 0; i < 3; i++) System.out.println(i);
		else
			for (int i = 9; i > 6; i--) System.out.println(i);
	}
}