import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyUtilsTest {
    @Test
    void getPropertyFromMap() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        var map = new HashMap<String, Object>();
        map.put("p1", "v1");
        map.put("p2", Map.of("p3", "v3"));
        assertThat(PropertyUtils.getProperty(map, "p2.p3")).isEqualTo("v3");
        assertThat(PropertyUtils.getProperty(map, "p1")).isEqualTo("v1");
    }
}