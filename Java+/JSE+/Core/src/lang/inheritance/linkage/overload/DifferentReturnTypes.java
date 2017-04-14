package lang.inheritance.linkage.overload;

import static java.lang.System.out;

/**
 * Перегружаемые методы отличаются только возвращаемыми типами и декларированными исключениями
 * (неприемлемо для перегруженных методов, поэтому добавлены разные параметры).
 */
public class DifferentReturnTypes {

    private void str(int a) {
        out.println("void str()");
    }

    private String str(Integer a) throws Exception {
        out.println("String str()");
        return null;
    }
}