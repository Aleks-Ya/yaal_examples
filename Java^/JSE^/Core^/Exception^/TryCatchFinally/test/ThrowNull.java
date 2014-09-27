import org.junit.Test;

import java.io.IOException;

public class ThrowNull {
    @Test
    public void main() throws IOException {
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
    
    private void throwNull() {
      throw null;
    }
    
    private void throwNullVar() throws IOException {
		IOException e = null;
		throw e;
	}
}