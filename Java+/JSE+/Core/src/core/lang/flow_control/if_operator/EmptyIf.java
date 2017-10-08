package core.lang.flow_control.if_operator;

import org.junit.Test;

/**
 * Варианты пустых if.
 */
public class EmptyIf {
    @Test
    public void emptyIfElse() throws Exception {
        if(false); else;
        if(true); else;
    }

    @Test
    public void emptyIf() throws Exception {
        if(false);
    }
}