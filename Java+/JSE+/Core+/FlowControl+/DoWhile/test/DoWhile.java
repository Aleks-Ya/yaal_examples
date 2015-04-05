import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoWhile {

    /**
     * Блок do без {} возможен для одного оператора.
     */
    @Test
    public void oneStatement() {
        int i = 0;
        do i++; while (i < 5);
        //do i++; i++; while (i < 5); //compile error: "while expected"
        assertEquals(5, i);
    }

    /**
     * Блок do без {} возможен для одного оператора.
     */
    @Test
    public void emptyStatement() {
        int i = 0;
        do ; while (i++ < 5);
        //do  while (i < 5); //compile error: "while expected"
        assertEquals(6, i);
    }
}