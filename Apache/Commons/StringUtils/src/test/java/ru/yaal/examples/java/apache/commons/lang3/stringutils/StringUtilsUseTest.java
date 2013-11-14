package ru.yaal.examples.java.apache.commons.lang3.stringutils;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: a.yablokov
 * Date: 14.11.13
 */
public class StringUtilsUseTest {
    @Test
    public void testMain() throws Exception {
        //Сравнение строк (безопасно с null)
        assertFalse(StringUtils.equalsIgnoreCase(null, ""));
        //Пустая строка
        assertTrue(StringUtils.isBlank(null));
    }
}
