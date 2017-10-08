package core.lang.flow_control.if_operator;

import org.junit.Test;

import static java.lang.System.out;

public class DeadCode {

    /**
     * Код if(false) {...} мертвым НЕ считается.
     */
    @Test
    public void main() {
        if (false) {
            out.println("false!");
        }
        out.println("I'm alive!");
    }
}