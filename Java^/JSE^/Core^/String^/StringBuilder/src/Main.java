import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
		StringBuilder sb = new StringBuilder("0123456");
		sb.replace(1,2,"abcde");
		out.println(sb);
    }
}
