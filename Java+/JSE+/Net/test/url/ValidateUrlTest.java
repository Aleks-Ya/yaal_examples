package url;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidateUrlTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
    @Test
    public void correct() throws MalformedURLException {
        new URL("https://www.ya.ru");
        new URL("http", "www.ya.ru", "path");
    }
    
    @Test
    public void noProtocol() throws MalformedURLException {
    	exception.expect(MalformedURLException.class);
    	exception.expectMessage("no protocol: www.ya.ru");
        new URL("www.ya.ru");
    }
}
