import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsTest {

    @Test
    void isNotEmpty() {
        var isNotEmpty = CollectionUtils.isNotEmpty(new ArrayList<>(0));
        assertThat(isNotEmpty).isFalse();
    }
}