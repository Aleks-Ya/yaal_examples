package net.url;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
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
    
    @Test
    public void hasProtocol() {
    	Pattern pattern = Pattern.compile("^\\w+://.+");
    	assertTrue(pattern. matcher("http://ya.ru/path?a=b&c=d").matches());
    	assertFalse(pattern.matcher("ya.ru/path?a=b&c=d").matches());
    }
}
