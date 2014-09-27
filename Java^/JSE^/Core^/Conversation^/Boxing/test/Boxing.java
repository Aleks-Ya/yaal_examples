import org.junit.Test;

public class Boxing {

    /**
     * Создание обертки.
     */
    @Test
    public void integer() {
        Integer a = 2;//Integer.valueOf(2)
        System.out.println(a);
    }

    /**
     * В выражении участвует обертка и примитив.
     */
    @Test
    public void expression() {
        Integer a = 2;//Integer.valueOf(2)
        Integer b = a * 3;//Integer.intValue(2) * 3 --> Integer.valueOf(6)
        System.out.println(b);
    }

    /**
     * В выражении участвуют только обертки.
     */
    @Test
    public void expression2() {
        Integer a = 2;//Integer.valueOf(2)
        Integer b = 3;//Integer.valueOf(3)
        Integer c = a * b;//Integer.intValue(a) * Integer.intValue(b) --> Integer.valueOf(6)
        System.out.println(c);
    }
}
