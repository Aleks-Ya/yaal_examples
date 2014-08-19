import static java.lang.System.out;

public class Main {
	
    public static void main(String[] args) {
		charIntByte();
    }
    
    static void charIntByte() {
		char c = 'a';
		int i = c;
		byte b = (byte) i;
		out.printf("char=%s int=%d byte=%d", c, i, b);
    }
}