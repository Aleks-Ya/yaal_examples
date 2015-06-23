package flow_control.switch_operator;

import org.junit.Test;

public class EmptySwitch {

    @Test
    public void main() {
		//switch(1); //Compile Error

		switch(1) {};

		switch(1) {
			case 2:
			default:
        }
    }
}