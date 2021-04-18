package scanners;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TeapotMock.class
})
public class TeapotTest {

    @Autowired
    private ITeapot teapot;

    @Test
    public void model() {
        assertEquals("TestSpot", teapot.getModel());
    }
}