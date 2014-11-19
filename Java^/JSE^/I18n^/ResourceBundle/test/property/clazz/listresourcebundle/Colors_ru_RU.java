package property.clazz.listresourcebundle;

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
                {HelloWorldListResourceBundle.LIGHT_KEY, Color.WHITE},
                {HelloWorldListResourceBundle.DARK_KEY, Color.BLACK}
        };
    }
}