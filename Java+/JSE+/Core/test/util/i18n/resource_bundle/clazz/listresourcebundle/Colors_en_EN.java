package util.i18n.resource_bundle.clazz.listresourcebundle;

import java.awt.Color;
import java.util.ListResourceBundle;

/**
 * Пакет ресурсов для английского языка.
 * Класс обязательно должен быть public.
 */
public class Colors_en_EN extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {HelloWorldListResourceBundleTest.LIGHT_KEY, Color.YELLOW},
                {HelloWorldListResourceBundleTest.DARK_KEY, Color.GRAY}
        };
    }
}