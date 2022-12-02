package lang.flow_control.do_while;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DoWhileTest {

    /**
     * Блок do без {} возможен для одного оператора.
     */
    @Test
    void oneStatement() {
        int i = 0;
        do i++; while (i < 5);
        //do i++; i++; while (i < 5); //compile error: "while expected"
        assertThat(i).isEqualTo(5);
    }

    /**
     * Блок do без {} возможен для одного оператора.
     */
    @Test
    void emptyStatement() {
        int i = 0;
        do ; while (i++ < 5);
        //do  while (i < 5); //compile error: "while expected"
        assertThat(i).isEqualTo(6);
    }
}