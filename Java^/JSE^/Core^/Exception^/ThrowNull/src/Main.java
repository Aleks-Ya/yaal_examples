import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
      try {
		  throwNull();
	  } catch (Exception e) {
		  System.out.println(e);
	  }
	  
	  try {
		  throwNullVar();
	  } catch (Exception e) {
		  System.out.println(e);
	  }
    }
    
    static void throwNull() {
      throw null;
    }
    
    static void throwNullVar() throws IOException {
		IOException e = null;
		throw e;
	}
}