package subtype;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Set;

/**
 * Поиск с помощью {@linkplain org.reflections.scanners.SubTypesScanner}
 */
public class BySubTypeScannerTest {

    @Test
    public void interfaceRealizationSearch() {
        System.out.println("SubTypesScanner:");
        Reflections reflections = new Reflections(BySubTypeScannerTest.class.getPackage(), new SubTypesScanner());
        Set<Class<? extends ITree>> classes = reflections.getSubTypesOf(ITree.class);
        for (Class clazz : classes) {
            System.out.println(clazz.getCanonicalName());
        }
        System.out.println();
    }

}