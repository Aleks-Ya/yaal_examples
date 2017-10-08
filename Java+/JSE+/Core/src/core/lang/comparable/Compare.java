package core.lang.comparable;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Compare {
    
    @Test
    public void numbers() {
    	Integer i = new Integer(100);
		Assert.assertEquals(0,  i.compareTo(100));
		Assert.assertEquals(1,  i.compareTo(99));
		Assert.assertEquals(-1, i.compareTo(101));
    }
}
