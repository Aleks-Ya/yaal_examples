import org.junit.Test;
import static org.junit.Assert.*;
import static java.lang.System.out;

public class Main {
    
    @Test
    public void charIntByte() {
		char c = 'a';
		int i = c;
		byte b = (byte) i;
		out.printf("char=%s int=%d byte=%d", c, i, b);
    }

 	/**
     * Присвоение примитивных типов без приведения.
     */
     @Test
    public void integerNums() {
    	final byte b = 1;
    	final short s = 10_000;
    	final int i = 1_000_000;
    	final long l = 5_000_000_000L;

    	long l2 = b;
    	l2 = s;
    	l2 = i;

    	int i2 = b;
    	i2 = s;

    	short s2 = b;
    }
    
    /**
     * Приведение примитивных типов без переполнения.
     */
    @Test
    public void integerNums2() {
    	final byte b = 1;
    	final short s = 2;
    	final int i = 3;
    	final long l = 4L;

    	long l2 = b;
    	l2 = s;
    	l2 = i;
    	l2 = l;

    	int i2 = b;
    	i2 = s;
    	i2 = i;
    	i2 = (int) l;

    	short s2 = b;
    	s2 = s;
    	s2 = (short) i;
    	s2 = (short) l;

    	byte b2 = b;
    	b2 = (byte) s;
    	b2 = (byte) i;
    	b2 = (byte) l;
    }

    /**
     * Приведение примитивных типов с переполнением.
     */
     @Test
    public void integerNums3() {
    	final byte b = 1;
    	final short s = 10_000;
    	final int i = 1_000_000;
    	final long l = 5_000_000_000L;

    	long l2 = b;
    	l2 = s;
    	l2 = i;
    	l2 = l;

    	int i2 = b;
    	i2 = s;
    	i2 = i;
    	i2 = (int) l;

    	short s2 = b;
    	s2 = s;
    	s2 = (short) i;
    	s2 = (short) l;

    	byte b2 = b;
    	b2 = (byte) s;
    	b2 = (byte) i;
    	b2 = (byte) l;
    }
}
