package bean.inherit.component;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = InheritComponentConfig.class)
class InheritComponentTest {

    @Autowired
    private Child child;

    @Test
    void name() {
        assertThat(child).isNotNull();
    }
}
