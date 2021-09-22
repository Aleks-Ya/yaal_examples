package lang.flow_control.if_operator;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class Main {

    /**
     * if-else без скобок.
     */
    @Test
    void withoutBraces() {
        if (1 < 2) 
          for (int i = 0; i < 3; i++) out.println(i);
        else
          for (int i = 9; i > 6; i--) out.println(i);
    }
    
    /**
     * else относится ко 2му if.
     */
    @Test
    void multiWithoutBraces() {
        if (false) out.println("if 1");
        if (true) out.println("if 2");
        else out.println("else");
    }
}