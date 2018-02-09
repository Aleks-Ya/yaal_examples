package scanners;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = {
        TeapotMock.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class TeapotTest {

    @Autowired
    private ITeapot teapot;

    @Test
    public void model() {
        assertEquals("TestSpot", teapot.getModel());
    }
}