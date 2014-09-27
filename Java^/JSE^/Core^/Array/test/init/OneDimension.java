package init;

import org.junit.Test;

/**
 * Инициализация одномерных массивов.
 */
public class OneDimension {
    @Test
    public void main() {
        {
            String[] arr = {"a"};
            String s = arr[0];
        }
        {
            //Compile error:
            //String s = {"a"}[0];
            String s = new String[]{"a"}[0];
        }
    }
}