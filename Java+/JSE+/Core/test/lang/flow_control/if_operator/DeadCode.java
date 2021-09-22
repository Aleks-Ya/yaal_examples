package lang.flow_control.if_operator;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class DeadCode {

    /**
     * Код if(false) {...} мертвым НЕ считается.
     */
    @Test
    void main() {
        if (false) {
            out.println("false!");
        }
        out.println("I'm alive!");
    }
}