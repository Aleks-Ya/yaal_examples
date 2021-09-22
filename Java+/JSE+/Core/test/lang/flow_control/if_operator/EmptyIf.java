package lang.flow_control.if_operator;

import org.junit.jupiter.api.Test;

/**
 * Варианты пустых if.
 */
class EmptyIf {
    @Test
    void emptyIfElse() throws Exception {
        if(false); else;
        if(true); else;
    }

    @Test
    void emptyIf() throws Exception {
        if(false);
    }
}