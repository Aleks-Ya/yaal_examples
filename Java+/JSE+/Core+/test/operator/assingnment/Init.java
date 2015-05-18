package operator.assingnment;

import org.junit.Test;
import static org.junit.Assert.*;

public class Init {

	@Test
    public void test() {
        byte b = 127;
        short s = 1128;
		double d = 10;
        double d2 = 2_000_000_000_000d;
        float f = 1;
        float f2 = 2_000_000_000_000f;
        long l = 3;
        System.out.println(d + f + f2 + l);
    }
}
