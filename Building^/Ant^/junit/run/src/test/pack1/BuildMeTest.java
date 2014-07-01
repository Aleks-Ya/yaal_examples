package pack1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuildMeTest {
    @Test
    public void getEmptyString() throws Exception {
        BuildMe bm = new BuildMe();
        assertEquals("", bm.getEmptyString());
    }
}
