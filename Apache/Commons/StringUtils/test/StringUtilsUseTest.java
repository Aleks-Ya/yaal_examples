import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsUseTest {
    @Test
    public void testMain() throws Exception {
        //Сравнение строк (безопасно с null)
        assertFalse(StringUtils.equalsIgnoreCase(null, ""));
        //Пустая строка
        assertTrue(StringUtils.isBlank(null));
    }
}
