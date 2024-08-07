package lang.conversation.boxing;

import org.junit.jupiter.api.Test;

class BoxingTest {

    /**
     * Создание обертки.
     */
    @Test
    void integer() {
        Integer a = 2;//Integer.valueOf(2)
        System.out.println(a);
    }

    /**
     * В выражении участвует обертка и примитив.
     */
    @Test
    void expression() {
        Integer a = 2;//Integer.valueOf(2)
        Integer b = a * 3;//Integer.intValue(2) * 3 --> Integer.valueOf(6)
        System.out.println(b);
    }

    /**
     * В выражении участвуют только обертки.
     */
    @Test
    void expression2() {
        Integer a = 2;//Integer.valueOf(2)
        Integer b = 3;//Integer.valueOf(3)
        Integer c = a * b;//Integer.intValue(a) * Integer.intValue(b) --> Integer.valueOf(6)
        System.out.println(c);
    }
}
