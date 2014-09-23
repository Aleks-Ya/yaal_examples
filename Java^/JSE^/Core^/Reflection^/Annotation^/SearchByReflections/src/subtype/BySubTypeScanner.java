package subtype;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Set;

/**
 * Поиск с помощью {@linkplain org.reflections.scanners.SubTypesScanner}
 */
public class BySubTypeScanner {
    public static void main(String[] args) {
        interfaceRealizationSearch();
    }

    private static void interfaceRealizationSearch() {
        System.out.println("SubTypesScanner:");
        Reflections reflections = new Reflections(BySubTypeScanner.class.getPackage(), new SubTypesScanner());
        Set<Class<? extends ITree>> classes = reflections.getSubTypesOf(ITree.class);
        for (Class clazz : classes) {
            System.out.println(clazz.getCanonicalName());
        }
        System.out.println();
    }

}