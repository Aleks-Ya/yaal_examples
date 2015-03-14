package annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = {Teapot.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class TeapotTest {

    @Autowired
    private Teapot teapot;

    @Test
    public void model() {
        assertEquals("Spot", teapot.getModel());
    }
}