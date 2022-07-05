package bean.inherit.component;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InheritComponentConfig.class)
class InheritComponentTest {

    @Autowired
    private Child child;

    @Test
    void name() {
        assertThat(child).isNotNull();
    }
}
