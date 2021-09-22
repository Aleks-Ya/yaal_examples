package lang.flow_control.switch_operator;

import org.junit.jupiter.api.Test;

class EmptySwitch {

    @Test
    void main() {
		//switch(1); //Compile Error

		switch(1) {};

		switch(1) {
			case 2:
			default:
        }
    }
}