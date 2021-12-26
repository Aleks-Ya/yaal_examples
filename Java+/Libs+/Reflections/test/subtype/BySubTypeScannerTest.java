package subtype;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * Поиск с помощью {@linkplain org.reflections.scanners.SubTypesScanner}
 */
class BySubTypeScannerTest {

    @Test
    void interfaceRealizationSearch() {
        System.out.println("SubTypesScanner:");
        var reflections = new Reflections(BySubTypeScannerTest.class.getPackage().getName(), Scanners.SubTypes);
        var classes = reflections.getSubTypesOf(ITree.class);
        for (Class<?> clazz : classes) {
            System.out.println(clazz.getCanonicalName());
        }
        System.out.println();
    }

    /**
     * Не работает. См. Guava (com.google.common.reflect.ClassPath)
     */
    @Test
    void allClassesInPackage() {
        System.out.println("allClassesInPackage:");
        var reflections = new Reflections(BySubTypeScannerTest.class.getPackage().getName(), Scanners.SubTypes.filterResultsBy(s -> false));
        var classes = reflections.getSubTypesOf(Object.class);
        for (var clazz : classes) {
            System.out.println(clazz.getName());
        }
        System.out.println();
    }

}