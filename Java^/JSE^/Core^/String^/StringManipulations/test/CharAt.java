import org.junit.Test;
import static org.junit.Assert.*;

public class CharAt {

    @Test
    public void substring() {
        String s = "0123";
        assertEquals('1',   s.charAt(1));
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void substringException1() {
    	"0".charAt(1);
    }
}