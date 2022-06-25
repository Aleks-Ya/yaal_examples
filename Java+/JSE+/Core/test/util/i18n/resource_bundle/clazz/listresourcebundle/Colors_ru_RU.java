package util.i18n.resource_bundle.clazz.listresourcebundle;

import java.awt.Color;
import java.util.ListResourceBundle;

/**
 * Пакет ресурсов для русского языка.
 * Класс обязательно должен быть public.
 */
public class Colors_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {HelloWorldListResourceBundleTest.LIGHT_KEY, Color.WHITE},
                {HelloWorldListResourceBundleTest.DARK_KEY, Color.BLACK}
        };
    }
}