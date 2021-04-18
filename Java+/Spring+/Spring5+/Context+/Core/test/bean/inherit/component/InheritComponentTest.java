package bean.inherit.component;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = InheritComponentConfig.class)
public class InheritComponentTest {

    @Autowired
    private Child child;

    @Test
    public void name() throws Exception {
        assertNotNull(child);
    }
}
