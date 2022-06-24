package bean.inherit.component;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InheritComponentConfig.class)
public class InheritComponentTest {

    @Autowired
    private Child child;

    @Test
    void name() throws Exception {
        assertNotNull(child);
    }
}
