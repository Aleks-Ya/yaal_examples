public class EmptySwitch {
    public static void main(String[] args) {
		//switch(1); //Compile Error
		
		switch(1) {};
		
		switch(1) {
			case 2:
			default:
        }
    }
}