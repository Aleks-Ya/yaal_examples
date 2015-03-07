import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;

public class CollectionUtilsUse {

    @Test
    public void isNotEmpty() {
        assertFalse(CollectionUtils.isNotEmpty(new ArrayList(0)));
    }
}