package scanners;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

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